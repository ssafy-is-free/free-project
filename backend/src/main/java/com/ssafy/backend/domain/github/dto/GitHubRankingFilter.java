package com.ssafy.backend.domain.github.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GitHubRankingFilter {
	private Long language;

	public boolean isNull() {
		return language == null;
	}

}
