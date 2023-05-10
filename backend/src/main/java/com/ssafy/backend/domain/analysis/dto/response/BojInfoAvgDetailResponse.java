package com.ssafy.backend.domain.analysis.dto.response;

import java.util.List;

import com.ssafy.backend.domain.algorithm.dto.response.BojLanguageResponse;
import com.ssafy.backend.domain.analysis.dto.BojAvgDetail;
import com.ssafy.backend.domain.util.service.TierValueFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BojInfoAvgDetailResponse {
	private String tierUrl;
	private Double pass;
	private Double tryFail;
	private Double submit;
	private Double fail;
	private List<BojLanguageResponse> languages;

	public static BojInfoAvgDetailResponse create(BojAvgDetail bojAvgDetail,
		List<BojLanguageResponse> languages) {
		return BojInfoAvgDetailResponse.builder()
			.tierUrl(TierValueFormatter.format(bojAvgDetail.getAvgTier()))
			.pass(bojAvgDetail.getAvgPassCount())
			.tryFail(bojAvgDetail.getAvgTryFailCount())
			.submit(bojAvgDetail.getAvgSubmitCount())
			.fail(bojAvgDetail.getAvgFailCount())
			.languages(languages)
			.build();
	}
}
