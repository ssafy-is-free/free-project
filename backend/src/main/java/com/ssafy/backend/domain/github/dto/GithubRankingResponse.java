package com.ssafy.backend.domain.github.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GithubRankingResponse {
	List<GithubRankingCover> ranks;

	public static GithubRankingResponse create(List<GithubRankingCover> githubCovers) {
		return GithubRankingResponse.builder().ranks(githubCovers).build();
	}

	public void setRank(long rank) {
		for (GithubRankingCover rankingCover : this.ranks) {
			long rankUpDown = rankingCover.getRankUpDown(rank);
			rankingCover.updateRankInfo(rank, rankUpDown);
			rank++;
		}
	}
}
