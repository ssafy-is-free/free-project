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
public class BojRankComparisonResponse {
	BojInfoDetailResponse my;
	BojInfoDetailResponse opponent;

	public static BojRankComparisonResponse createEmpty() {
		return BojRankComparisonResponse.builder().build();
	}

	public static BojRankComparisonResponse create(BojInfoDetailResponse my, BojInfoDetailResponse opponent) {
		return BojRankComparisonResponse.builder().my(my).opponent(opponent).build();
	}

	public boolean checkForNull() {
		return this.my == null || this.opponent == null;
	}
}
