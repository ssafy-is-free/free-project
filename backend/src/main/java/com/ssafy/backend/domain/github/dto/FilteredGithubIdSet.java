package com.ssafy.backend.domain.github.dto;

import java.util.Set;

import lombok.Getter;

@Getter
public class FilteredGithubIdSet {
	Set<Long> githubIds;

	public FilteredGithubIdSet(Set<Long> githubIds) {
		this.githubIds = githubIds;
	}
}
