package com.ssafy.backend.domain.algorithm.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FilteredBojIdSet {
	Set<Long> BojIds;

	public static FilteredBojIdSet create(Set<Long> githubIds) {
		return FilteredBojIdSet.builder().BojIds(githubIds).build();
	}

	public boolean isNotIn(long userId) {
		return !(this.BojIds.contains(userId));
	}
}
