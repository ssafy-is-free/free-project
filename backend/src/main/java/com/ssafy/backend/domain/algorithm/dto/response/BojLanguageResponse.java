package com.ssafy.backend.domain.algorithm.dto.response;

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
public class BojLanguageResponse {
	private String name;
	private String passPercentage;

	public static BojLanguageResponse create(String name, String passPercentage) {
		return BojLanguageResponse.builder()
			.name(name)
			.passPercentage(passPercentage)
			.build();
	}
}
