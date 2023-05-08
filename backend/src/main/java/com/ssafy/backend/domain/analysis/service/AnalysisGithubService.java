package com.ssafy.backend.domain.analysis.service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.backend.domain.analysis.dto.response.CompareGithubResponse;
import com.ssafy.backend.domain.analysis.dto.response.GithubVsInfo;
import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.JobHistory;
import com.ssafy.backend.domain.entity.JobPosting;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.github.dto.FilteredGithubIdSet;
import com.ssafy.backend.domain.github.dto.FilteredUserIdSet;
import com.ssafy.backend.domain.github.dto.GithubDetailLanguage;
import com.ssafy.backend.domain.github.repository.GithubRepoRepository;
import com.ssafy.backend.domain.github.repository.GithubRepository;
import com.ssafy.backend.domain.github.repository.querydsl.GithubLanguageQueryRepository;
import com.ssafy.backend.domain.github.repository.querydsl.GithubQueryRepository;
import com.ssafy.backend.domain.job.repository.JobHistoryRepository;
import com.ssafy.backend.domain.job.repository.JobPostingRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.global.response.exception.CustomException;
import com.ssafy.backend.global.response.exception.CustomExceptionStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnalysisGithubService {
	private final JobHistoryRepository jobHistoryRepository;
	private final JobPostingRepository jobPostingRepository;
	private final GithubRepository githubRepository;
	private final GithubRepoRepository githubRepoRepository;
	private final GithubQueryRepository githubQueryRepository;
	private final UserRepository userRepository;
	private final GithubLanguageQueryRepository githubLanguageQueryRepository;

	public CompareGithubResponse compareWithOpponent(long opponentUserId, long myUserId) {
		//나의 정보
		GithubVsInfo myVsInfo = getVsInfo(myUserId);

		//상대방 정보
		GithubVsInfo opponentVsInfo = getVsInfo(opponentUserId);
		opponentVsInfo.toOneDecimal();

		return CompareGithubResponse.create(myVsInfo, opponentVsInfo);
	}

	public CompareGithubResponse compareWithAllApplicant(long jobPostingId, long myUserId) {
		//나의 정보
		GithubVsInfo myVsInfo = getVsInfo(myUserId);

		log.info("지원자 평균 정보 조회");
		//지원자 전체 정보
		GithubVsInfo applicantVsInfo = getVsInfoByJobPosting(jobPostingId, myUserId);

		return CompareGithubResponse.create(myVsInfo, applicantVsInfo);

	}

	private GithubVsInfo getVsInfoByJobPosting(long jobPostingId, long myUserId) {
		// 특정 공고에 지원한 유저들의 아이디를 얻는다.
		FilteredUserIdSet filteredUserIdSet = getUserIdByJobPosting(jobPostingId);

		if (filteredUserIdSet == null || filteredUserIdSet.isNotIn(myUserId)) {
			throw new CustomException(CustomExceptionStatus.NOT_APPLY);
		}

		// 깃허브 스타, 커밋 정보
		GithubVsInfo applicantVsInfo = githubQueryRepository.findAvgByApplicant(filteredUserIdSet);

		// 유저 아이디를 기반으로 깃허브 아이디를 얻는다.
		FilteredGithubIdSet filteredGithubIdSet = getGithubIdSetByUserIdSet(filteredUserIdSet);

		// 리포지토리 평균 개수
		double repoCountAvg = getRepoCountAvg(filteredGithubIdSet);

		// 언어 사용 평균
		List<GithubDetailLanguage> applicantLanguageInfo = getLanguageInfo(filteredGithubIdSet);

		applicantVsInfo.updateLanguages(applicantLanguageInfo);
		applicantVsInfo.updateRepositories(repoCountAvg);
		applicantVsInfo.toOneDecimal();
		return applicantVsInfo;
	}

	private List<GithubDetailLanguage> getLanguageInfo(FilteredGithubIdSet filteredGithubIdSet) {
		// languageId 그룹별 percent 평균
		long size = filteredGithubIdSet.getSize();
		return githubLanguageQueryRepository.findAvgGroupByLanguage(filteredGithubIdSet)
			.stream()
			.peek(g -> g.dividePercentage(size))
			.collect(Collectors.toList());
	}

	private double getRepoCountAvg(FilteredGithubIdSet filteredGithubIdSet) {
		long allRepoCount = githubRepoRepository.countByGithubIdIn(filteredGithubIdSet.getGithubIds());
		return (double)allRepoCount / filteredGithubIdSet.getSize();

	}

	private FilteredGithubIdSet getGithubIdSetByUserIdSet(FilteredUserIdSet filteredUserIdSet) {
		Set<Long> githubIds = githubRepository.findByUserIdIn(filteredUserIdSet.getUserIds())
			.stream()
			.map(Github::getId)
			.collect(Collectors.toSet());
		return FilteredGithubIdSet.create(githubIds);
	}

	private FilteredUserIdSet getUserIdByJobPosting(long jobPostingId) {
		JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_JOBPOSTING));
		List<JobHistory> jobHistoryList = jobHistoryRepository.findByJobPosting(jobPosting);

		if (jobHistoryList.isEmpty()) {
			return null;
		}

		return FilteredUserIdSet.create(jobHistoryList);
	}

	private GithubVsInfo getVsInfo(long myUserId) {
		User user = userRepository.findById(myUserId)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_USER));

		Github github = githubRepository.findByUser(user)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_GITHUB));

		//언어 정보
		List<GithubDetailLanguage> languageList = getDetailLanguages(github);

		//레포 정보
		long countRepo = githubRepoRepository.countByGithub(github);
		return GithubVsInfo.create(github, languageList, countRepo);
	}

	private List<GithubDetailLanguage> getDetailLanguages(Github github) {
		return githubLanguageQueryRepository.findByGithub(github)
			.stream()
			.sorted(Comparator.comparingDouble(l -> -l.getPercentage()))
			.collect(Collectors.toList());

	}

}

