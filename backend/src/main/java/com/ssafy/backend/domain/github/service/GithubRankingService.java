package com.ssafy.backend.domain.github.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.github.dto.FilteredGithubIdSet;
import com.ssafy.backend.domain.github.dto.GitHubRankingFilter;
import com.ssafy.backend.domain.github.dto.GithubRankingResponse;
import com.ssafy.backend.domain.github.repository.GithubLanguageRepository;
import com.ssafy.backend.domain.github.repository.querydsl.GithubQueryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubRankingService {
	private final GithubQueryRepository githubQueryRepository;
	private final GithubLanguageRepository githubLanguageRepository;

	public GithubRankingResponse getGithubRank(long rank, Long userId, Integer score, GitHubRankingFilter rankingFilter,
		Pageable pageable) {

		FilteredGithubIdSet githubIdSet = rankingFilter.isNull() ? null : getGithubIdBy(rankingFilter);

		List<Github> githubList = githubQueryRepository.findAll(userId, score, githubIdSet, pageable);
		GithubRankingResponse githubRankingResponse = GithubRankingResponse.create(githubList);
		githubRankingResponse.setRank(rank);

		log.info(githubRankingResponse.toString());
		return githubRankingResponse;
	}

	private FilteredGithubIdSet getGithubIdBy(GitHubRankingFilter rankingFilter) {
		Set<Long> filterdIdSet = githubLanguageRepository.findByLanguageId(rankingFilter.getLanguage())
			.stream()
			.map(g -> g.getGithub().getId())
			.collect(Collectors.toSet());

		return FilteredGithubIdSet.create(filterdIdSet);
	}

}
