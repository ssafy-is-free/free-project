package com.ssafy.backend.domain.github.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GitHubRankingFilter {
	private List<Long> language;

}
