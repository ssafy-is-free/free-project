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
		GithubVsInfo myVsInfo = getGithubVsInfo(myUserId);

		//상대방 정보
		GithubVsInfo opponentVsInfo = getGithubVsInfo(opponentUserId);
		opponentVsInfo.toOneDecimal();

		return CompareGithubResponse.create(myVsInfo, opponentVsInfo);
	}

	public CompareGithubResponse compareWithAllApplicant(long jobPostingId, long myUserId) {
		//나의 정보
		GithubVsInfo myVsInfo = getGithubVsInfo(myUserId);

		log.info("지원자 평균 정보 조회");
		//지원자 전체 정보

		// 특정 공고에 지원한 유저들의 아이디를 얻는다.
		JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_JOBPOSTING));
		List<JobHistory> jobHistoryList = jobHistoryRepository.findByJobPosting(jobPosting);

		FilteredUserIdSet filteredUserIdSet = FilteredUserIdSet.create(jobHistoryList);

		if (filteredUserIdSet.isEmpty() || filteredUserIdSet.isNull()) {
			throw new CustomException(CustomExceptionStatus.NOT_FOUND_APPLICANT);
		}

		// 얻은 아이디를  where 조건에 넣고 깃허브 정보 평균 구하기
		GithubVsInfo applicantVsInfo = githubQueryRepository.findAvgByApplicant(filteredUserIdSet);

		// 유저 아이디를 기반으로 깃허브 아이디를 얻는다.
		Set<Long> githubIds = githubRepository.findByUserIdIn(filteredUserIdSet.getUserIds())
			.stream()
			.map(Github::getId)
			.collect(Collectors.toSet());
		FilteredGithubIdSet filteredGithubIdSet = FilteredGithubIdSet.create(githubIds);

		// 얻은 아이디를 where 조건에 넣고 깃허브레포 count 쿼리
		long allRepoCount = githubRepoRepository.countByGithubIdIn(filteredGithubIdSet.getGithubIds());
		double repoCountAvg = (double)allRepoCount / filteredGithubIdSet.getSize();
		// double applicantRepoInfo = Math.round(repoCountAvg + 10) / 10.0;

		// 얻은 아이디를 where 조건에 넣고 깃허브언어에서 언어 아이디로 Group By 후 percent 평균
		long size = filteredGithubIdSet.getSize();
		List<GithubDetailLanguage> applicantLanguageInfo = githubLanguageQueryRepository.findAvgGroupByLanguage(
			filteredGithubIdSet).stream().peek(g -> g.dividePercentage(size)).collect(Collectors.toList());

		applicantVsInfo.updateLanguages(applicantLanguageInfo);
		applicantVsInfo.updateRepositories(repoCountAvg);
		applicantVsInfo.toOneDecimal();

		return CompareGithubResponse.create(myVsInfo, applicantVsInfo);

	}

	// TODO: 2023-05-04 본인 정보 조회는 토큰에서 아이디 가져오는 시점에 예외 처리함 안해도 ㄱㅊ!
	private GithubVsInfo getGithubVsInfo(long myUserId) {
		User user = userRepository.findById(myUserId)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_USER));

		Github github = githubRepository.findByUser(user)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_GITHUB));

		//언어 정보
		List<GithubDetailLanguage> languageList = getDetailLanguages(github);
		return GithubVsInfo.create(github, languageList);
	}

	private List<GithubDetailLanguage> getDetailLanguages(Github github) {
		return githubLanguageQueryRepository.findByGithub(github)
			.stream()
			.sorted(Comparator.comparingDouble(l -> -l.getPercentage()))
			.collect(Collectors.toList());

	}

}

