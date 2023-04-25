package com.ssafy.backend.domain.github.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class GithubRankingResponse {
	List<GithubRankingCover> ranks;

	public GithubRankingResponse(List<GithubRankingCover> githubCovers) {
		this.ranks = githubCovers;
	}
}
