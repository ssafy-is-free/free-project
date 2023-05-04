package com.ssafy.backend.domain.analysis.dto;

import java.util.List;

import com.ssafy.backend.domain.algorithm.dto.response.BojLanguageResponse;
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

	public boolean checkForNull() {
		return this.tierUrl == null && this.pass == 0 &&
			this.tryFail == 0 && this.submit == 0 && this.fail == 0 &&
			(this.languages == null || this.languages.isEmpty());
	}

	public static BojInfoAvgDetailResponse createEmpty() {
		return BojInfoAvgDetailResponse.builder().build();
	}

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
