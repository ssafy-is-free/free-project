package com.ssafy.backend.domain.github.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.github.dto.FilteredGithubIdSet;
import com.ssafy.backend.domain.github.dto.GitHubRankingFilter;
import com.ssafy.backend.domain.github.dto.GithubRankingCover;
import com.ssafy.backend.domain.github.dto.GithubRankingOneResponse;
import com.ssafy.backend.domain.github.dto.GithubRankingResponse;
import com.ssafy.backend.domain.github.repository.GithubLanguageRepository;
import com.ssafy.backend.domain.github.repository.GithubRepository;
import com.ssafy.backend.domain.github.repository.querydsl.GithubQueryRepository;
import com.ssafy.backend.global.response.exception.CustomException;
import com.ssafy.backend.global.response.exception.CustomExceptionStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubRankingService {
	private final GithubQueryRepository githubQueryRepository;
	private final GithubLanguageRepository githubLanguageRepository;
	private final GithubRepository githubRepository;

	public GithubRankingResponse getGithubRank(Long rank, Long userId, Integer score, GitHubRankingFilter rankingFilter,
		Pageable pageable) {
		//필터링된 githubId List
		FilteredGithubIdSet githubIdSet = rankingFilter.isNull() ? null : getGithubIdBy(rankingFilter);

		//페이지네이션된 깃허브 데이터
		List<Github> githubList = githubQueryRepository.findAll(userId, score, githubIdSet, pageable);
		GithubRankingResponse githubRankingResponse = GithubRankingResponse.create(githubList);

		//랭킹 정보 설정
		setRankInfo(rank, !rankingFilter.isNull(), githubRankingResponse);

		log.info(githubRankingResponse.toString());
		return githubRankingResponse;
	}

	private void setRankInfo(Long rank, boolean withFilter, GithubRankingResponse githubRankingResponse) {

		long prevRank = rank != null ? rank+1 : 1;
		//필터 검색이 아닐때만 랭킹폭 세팅
		if (withFilter) {
			githubRankingResponse.updateRank(prevRank);
		} else {
			githubRankingResponse.updateRankAnRankUpDown(prevRank);
		}
	}

	private FilteredGithubIdSet getGithubIdBy(GitHubRankingFilter rankingFilter) {
		Set<Long> filterdIdSet = githubLanguageRepository.findByLanguageId(rankingFilter.getLanguageId())
			.stream()
			.map(g -> g.getGithub().getId())
			.collect(Collectors.toSet());

		return FilteredGithubIdSet.create(filterdIdSet);
	}

	@Transactional
	public GithubRankingOneResponse getGithubRankOne(long userId, GitHubRankingFilter rankingFilter) {
		// 필터에 걸리는 유저 아이디들을 불러온다.
		FilteredGithubIdSet githubIdSet = rankingFilter.isNull() ? null : getGithubIdBy(rankingFilter);

		// 내가 속해있는지 확인하기
		if (githubIdSet != null && githubIdSet.isNotIn(userId)) {
			return GithubRankingOneResponse.createEmpty();
		}

		// 깃허브 불러오기
		Github github = githubRepository.findByUserId(userId)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_GITHUB));

		// 랭킹 계산
		int rank;
		if (githubIdSet == null) {
			rank = githubRepository.getRank(github.getScore());
		} else {
			rank = githubRepository.getRankWithFilter(githubIdSet.getGithubIds(), github.getScore());
		}
		rank += 1;

		// 깃허브 랭킹 커버
		GithubRankingCover githubRankingCover = GithubRankingCover.create(github);

		GithubRankingOneResponse githubRankingResponse = GithubRankingOneResponse.create(githubRankingCover);

		// githubList 사이즈 --> 랭킹
		if (githubIdSet != null) {
			githubRankingResponse.setRank(rank);
		} else {
			githubRankingResponse.setRankAndRankUpDown(rank);
		}

		return githubRankingResponse;
	}
}
