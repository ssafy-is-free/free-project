package com.ssafy.backend.domain.github.service;

import static com.ssafy.backend.domain.github.dto.FilteredGithubIdSet.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.JobPosting;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.github.dto.FilteredGithubIdSet;
import com.ssafy.backend.domain.github.dto.GitHubRankingFilter;
import com.ssafy.backend.domain.github.dto.GithubRankingCover;
import com.ssafy.backend.domain.github.dto.GithubRankingOneResponse;
import com.ssafy.backend.domain.github.dto.GithubRankingResponse;
import com.ssafy.backend.domain.github.repository.GithubLanguageRepository;
import com.ssafy.backend.domain.github.repository.GithubRepository;
import com.ssafy.backend.domain.github.repository.querydsl.GithubQueryRepository;
import com.ssafy.backend.domain.job.repository.JobHistoryQueryRepository;
import com.ssafy.backend.domain.job.repository.JobPostingRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.global.response.exception.CustomException;
import com.ssafy.backend.global.response.exception.CustomExceptionStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GithubRankingService {
	private final JobPostingRepository jobPostingRepository;
	private final GithubQueryRepository githubQueryRepository;
	private final GithubLanguageRepository githubLanguageRepository;
	private final JobHistoryQueryRepository jobHistoryQueryRepository;
	private final UserRepository userRepository;
	private final GithubRepository githubRepository;

	public GithubRankingResponse getGithubRank(Long rank, Long userId, Integer score, GitHubRankingFilter rankingFilter,
		Pageable pageable) {

		FilteredGithubIdSet githubIdSet = create();

		//언어로 필터링 githubIds
		githubIdSet.addIds(getGithubIdByLanguage(rankingFilter.getLanguageId()));

		// 필터링된 깃허브 아이디가 없는 경우 DB 조회 X
		if (rankingFilter.getLanguageId() != null && githubIdSet.isEmpty()) {
			return GithubRankingResponse.createEmpty();
		}

		//공고별로 필터링된 userIds
		githubIdSet.addIds(getUserIdByJobPosting(rankingFilter.getJobPostingId()));

		// 필터링된 유저 아이디가 없는 경우 DB 조회 X
		if (rankingFilter.getJobPostingId() != null && githubIdSet.isEmpty()) {
			return GithubRankingResponse.createEmpty();
		}

		//페이지네이션된 깃허브 데이터
		List<Github> githubList = githubQueryRepository.findAll(userId, score,
			githubIdSet.isEmpty() ? null : githubIdSet, pageable);
		GithubRankingResponse githubRankingResponse = GithubRankingResponse.create(githubList);

		//랭킹 정보 설정
		setRankInfo(rank, !rankingFilter.isNull(), githubRankingResponse);

		log.info(githubRankingResponse.toString());
		return githubRankingResponse;
	}

	private Set<Long> getUserIdByJobPosting(Long jobPostingId) {
		if (jobPostingId == null) {
			return Collections.emptySet();
		}
		//공고 유효성 검증
		JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_JOBPOSTING));

		return new HashSet<>(jobHistoryQueryRepository.findByPostingJonGithub(jobPosting));
	}

	private Set<Long> getGithubIdByLanguage(Long languageId) {
		if (languageId == null) {
			return Collections.emptySet();
		}
		return githubLanguageRepository.findByLanguageId(languageId)
			.stream()
			.map(g -> g.getGithub().getId())
			.collect(Collectors.toSet());
	}

	private void setRankInfo(Long rank, boolean withFilter, GithubRankingResponse githubRankingResponse) {

		long prevSearchRank = rank != null ? rank + 1 : 1;
		//필터 검색이 아닐때만 랭킹폭 세팅
		if (withFilter) {
			githubRankingResponse.updateRank(prevSearchRank);
		} else {
			githubRankingResponse.updateRankAnRankUpDown(prevSearchRank);
		}
	}

	public GithubRankingOneResponse getGithubRankOne(long userId, GitHubRankingFilter rankingFilter) {
		// 삭제된 유저인지 판단
		Optional<User> findUser = userRepository.findByIdAndIsDeletedFalse(userId);
		if (!findUser.isPresent()) {
			return GithubRankingOneResponse.createEmpty();
		}

		// 깃허브 불러오기
		Github github = githubRepository.findByUserId(userId)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_GITHUB));

		// 필터에 걸리는 깃허브 아이디들을 불러온다.
		FilteredGithubIdSet githubIdSet = FilteredGithubIdSet.create();

		githubIdSet.addIds(getGithubIdByLanguage(rankingFilter.getLanguageId()));

		// 필터링된 깃허브 아이디가 없는 경우 DB 조회 X
		if (rankingFilter.getLanguageId() != null && githubIdSet.isEmpty()) {
			return GithubRankingOneResponse.createEmpty();
		}

		//필터링된 깃허브에 해당 유저가 없는 경우
		if (rankingFilter.getLanguageId() != null && githubIdSet.isNotIn(github.getId())) {
			return GithubRankingOneResponse.createEmpty();
		}

		// 필터에 걸리는 유저 아이디들을 불러온다.
		githubIdSet.addIds(getUserIdByJobPosting(rankingFilter.getJobPostingId()));

		// 필터링된 유저 아이디가 없는 경우 DB 조회 X
		if (rankingFilter.getJobPostingId() != null && githubIdSet.isEmpty()) {
			return GithubRankingOneResponse.createEmpty();
		}

		//필터링된 유저 아이디에 해당 유저가 없는 경우
		if (rankingFilter.getJobPostingId() != null && githubIdSet.isNotIn(github.getId())) {
			return GithubRankingOneResponse.createEmpty();
		}

		// 랭킹 계산
		long rank;
		if (rankingFilter.isNull()) {
			rank = githubQueryRepository.getRank(github.getScore(), userId);
		} else {
			rank = githubQueryRepository.getRankWithFilter(githubIdSet, github.getScore(), userId);
		}
		rank += 1;

		// 깃허브 랭킹 커버
		GithubRankingCover githubRankingCover = GithubRankingCover.create(github);

		GithubRankingOneResponse githubRankingResponse = GithubRankingOneResponse.create(githubRankingCover);

		// githubList 사이즈 --> 랭킹
		if (rankingFilter.isNull()) {
			githubRankingResponse.setRankAndRankUpDown(rank);
		} else {
			githubRankingResponse.setRank(rank);
		}

		return githubRankingResponse;
	}

}
