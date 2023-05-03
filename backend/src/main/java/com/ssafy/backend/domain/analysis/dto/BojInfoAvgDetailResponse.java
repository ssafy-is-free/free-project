package com.ssafy.backend.domain.analysis.dto;

import java.util.List;

import net.minidev.json.annotate.JsonIgnore;

import com.ssafy.backend.domain.algorithm.dto.response.BojInfoDetailResponse;
import com.ssafy.backend.domain.algorithm.dto.response.BojLanguageResponse;
import com.ssafy.backend.domain.entity.Baekjoon;

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
	private int pass;
	private int tryFail;
	private int submit;
	private int fail;
	private List<BojLanguageResponse> languages;

	@JsonIgnore
	public boolean isNull() {
		return this.tierUrl == null && this.pass == 0 &&
			this.tryFail == 0 && this.submit == 0 && this.fail == 0 &&
			(this.languages == null || this.languages.isEmpty());
	}

	public static BojInfoDetailResponse createEmpty() {
		return BojInfoDetailResponse.builder().build();
	}

	public static BojInfoDetailResponse create(Baekjoon baekjoon,
		List<BojLanguageResponse> languageDTOList) {
		return BojInfoDetailResponse.builder()
			.tierUrl(baekjoon.getTier())
			.pass(baekjoon.getPassCount())
			.tryFail(baekjoon.getTryFailCount())
			.submit(baekjoon.getSubmitCount())
			.fail(baekjoon.getFailCount())
			.languages(languageDTOList)
			.build();
	}
}
