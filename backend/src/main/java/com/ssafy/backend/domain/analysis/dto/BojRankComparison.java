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
public class BojRankComparison {
	BojInfoDetailResponse my;
	BojInfoDetailResponse opponent;

	public static BojRankComparison createEmpty() {
		return BojRankComparison.builder().build();
	}

	public static BojRankComparison create(BojInfoDetailResponse my, BojInfoDetailResponse opponent) {
		return BojRankComparison.builder().my(my).opponent(opponent).build();
	}

	public boolean isEmpty() {
		return this.my == null || this.opponent == null;
	}
}
