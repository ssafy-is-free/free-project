package com.ssafy.backend.domain.analysis.dto;

import com.ssafy.backend.domain.algorithm.dto.response.BojInfoDetailResponse;
import com.ssafy.backend.domain.analysis.dto.response.BojInfoAvgDetailResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BojRankAllComparisonResponse {
	BojInfoDetailResponse my;
	BojInfoAvgDetailResponse other;

	public static BojRankAllComparisonResponse createEmpty() {
		return BojRankAllComparisonResponse.builder().build();
	}

	public static BojRankAllComparisonResponse create(BojInfoDetailResponse my, BojInfoAvgDetailResponse other) {
		return BojRankAllComparisonResponse.builder().my(my).other(other).build();
	}

	public boolean checkForNull() {
		return this.my == null || this.other == null;
	}
}
