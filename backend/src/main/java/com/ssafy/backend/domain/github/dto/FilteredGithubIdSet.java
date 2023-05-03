package com.ssafy.backend.domain.github.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FilteredGithubIdSet {
	Set<Long> githubIds;

	public static FilteredGithubIdSet create(Set<Long> githubIds) {
		return FilteredGithubIdSet.builder().githubIds(githubIds).build();
	}

	public boolean isEmpty() {
		return this.githubIds.isEmpty();
	}

	public boolean isNotIn(long userId) {
		return !(this.githubIds.contains(userId));
	}
}
