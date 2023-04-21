package com.ssafy.backend.domain.github.dto;

import java.util.List;

public class GithubRankingResponse {
	List<GithubRankingCover> ranks;

	public GithubRankingResponse(List<GithubRankingCover> ranks) {
		this.ranks = ranks;
	}
}
