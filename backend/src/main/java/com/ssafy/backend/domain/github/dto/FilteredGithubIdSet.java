package com.ssafy.backend.domain.github.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Builder
@Slf4j
@AllArgsConstructor
public class FilteredGithubIdSet {
	final Set<Long> githubIds;

	public static FilteredGithubIdSet create(Set<Long> githubIds) {
		return FilteredGithubIdSet.builder().githubIds(githubIds).build();
	}

	public static FilteredGithubIdSet create() {
		return FilteredGithubIdSet.builder().githubIds(new HashSet<>()).build();
	}

	public void addIds(Set<Long> newIds) {
		if (!newIds.isEmpty()) {
			this.githubIds.addAll(newIds);
		}
	}

	public boolean isEmpty() {
		return this.githubIds.isEmpty();
	}

	public boolean isNotIn(long userId) {
		return !(this.githubIds.contains(userId));
	}

	public int getSize() {
		return this.githubIds.size();
	}
}
