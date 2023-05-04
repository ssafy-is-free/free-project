package com.ssafy.backend.domain.github.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.ssafy.backend.domain.entity.JobHistory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FilteredUserIdSet {
	Set<Long> userIds;

	public static FilteredUserIdSet create(List<JobHistory> jobHistoryList) {
		Set<Long> userIds = jobHistoryList.stream().map(j -> j.getUser().getId()).collect(Collectors.toSet());

		return FilteredUserIdSet.builder().userIds(userIds).build();
	}

	public boolean isEmpty() {
		return this.userIds.isEmpty();
	}

	public boolean isNull() {
		return this.userIds == null;
	}

}
