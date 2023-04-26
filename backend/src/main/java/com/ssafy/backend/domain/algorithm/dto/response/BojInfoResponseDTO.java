package com.ssafy.backend.domain.algorithm.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BojInfoResponseDTO {
	@JsonProperty("tier")
	private String tier;

	@JsonProperty("pass_count")
	private int passCount;

	@JsonProperty("try_fail_count")
	private int tryFailCount;

	@JsonProperty("submit_count")
	private int submitCount;

	@JsonProperty("fail_count")
	private int failCount;

	@JsonProperty("languages_result")
	private List<BojLanguageResultDTO> languagesResult;

}
