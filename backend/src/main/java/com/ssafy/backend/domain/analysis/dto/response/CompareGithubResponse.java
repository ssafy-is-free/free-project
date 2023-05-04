package com.ssafy.backend.domain.analysis.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CompareGithubResponse {
	GithubVsInfo my;
	GithubVsInfo opponent;

	public static CompareGithubResponse create(GithubVsInfo my, GithubVsInfo opponent) {
		return CompareGithubResponse.builder().my(my).opponent(opponent).build();
	}

}
