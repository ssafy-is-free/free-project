package com.ssafy.backend.domain.github.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GitHubRankingFilter {
	private Long languageId;

	public boolean isNull() {
		return languageId == null;
	}

}
