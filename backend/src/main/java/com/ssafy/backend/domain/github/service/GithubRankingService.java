package com.ssafy.backend.domain.github.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.backend.domain.github.dto.FilteredGithubIdSet;
import com.ssafy.backend.domain.github.dto.GitHubRankingFilter;
import com.ssafy.backend.domain.github.dto.GithubRankingCover;
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

		List<GithubRankingCover> githubCovers = githubQueryRepository.findAll(userId, score, githubIdSet, pageable)
			.stream()
			.map(GithubRankingCover::new)
			.collect(Collectors.toList());

		setRankToResult(rank, githubCovers);

		log.info(githubCovers.toString());
		return new GithubRankingResponse(githubCovers);
	}

	private FilteredGithubIdSet getGithubIdBy(GitHubRankingFilter rankingFilter) {
		return new FilteredGithubIdSet(githubLanguageRepository.findByLanguageId(rankingFilter.getLanguage())
			.stream()
			.map(g -> g.getGithub().getId())
			.collect(Collectors.toSet()));
	}

	//랭킹 반영
	private static void setRankToResult(long rank, List<GithubRankingCover> rankingCovers) {
		for (GithubRankingCover rankingCover : rankingCovers) {
			rankingCover.setRank(rank);
			rankingCover.setRankUpDown(rankingCover.getPrevRank() - rank);
			rank++;
		}
	}

}
