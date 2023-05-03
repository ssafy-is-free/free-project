package com.ssafy.backend.domain.analysis.dto;

import com.ssafy.backend.domain.algorithm.dto.response.BojInfoDetailResponse;

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
public class BojRankAllComparison {
	BojInfoDetailResponse my;
	BojInfoAvgDetailResponse other;

	public static BojRankAllComparison createEmpty() {
		return BojRankAllComparison.builder().build();
	}

	public static BojRankAllComparison create(BojInfoDetailResponse my, BojInfoAvgDetailResponse other) {
		return BojRankAllComparison.builder().my(my).other(other).build();
	}

	public boolean isNull() {
		return this.my == null || this.other == null;
	}
}
