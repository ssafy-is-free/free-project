package com.ssafy.backend.domain.github.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GithubRankingOneResponse {

	GithubRankingCover githubRankingCover;

	public static GithubRankingOneResponse create(GithubRankingCover githubCover) {
		return GithubRankingOneResponse.builder().githubRankingCover(githubCover).build();
	}

	public static GithubRankingOneResponse createEmpty() {
		return GithubRankingOneResponse.builder().build();
	}

	public void setRank(long rank) {
		long rankUpDown = this.githubRankingCover.getRankUpDown(rank);
		this.githubRankingCover.setRank(rank);
		this.githubRankingCover.setRankUpDown(rankUpDown);
	}
}
