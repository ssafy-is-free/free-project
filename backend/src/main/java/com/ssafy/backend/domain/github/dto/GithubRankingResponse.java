package com.ssafy.backend.domain.github.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.backend.domain.entity.Github;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GithubRankingResponse {
	List<GithubRankingCover> ranks;

	public static GithubRankingResponse create(List<Github> githubList) {
		List<GithubRankingCover> githubRankingCovers = githubList.stream()
			.map(GithubRankingCover::create)
			.collect(Collectors.toList());

		return GithubRankingResponse.builder().ranks(githubRankingCovers).build();
	}

	public static GithubRankingResponse createEmpty() {
		return GithubRankingResponse.builder().ranks(Collections.emptyList()).build();
	}

	public void updateRank(long rank) {
		for (GithubRankingCover rankingCover : this.ranks) {
			rankingCover.setRank(rank);
			rank++;
		}
	}

	public void updateRankAnRankUpDown(long rank) {
		for (GithubRankingCover rankingCover : this.ranks) {
			long rankUpDown = rankingCover.getRankUpDown(rank);
			rankingCover.setRank(rank);
			rankingCover.setRankUpDown(rankUpDown);
			rank++;
		}
	}

}
