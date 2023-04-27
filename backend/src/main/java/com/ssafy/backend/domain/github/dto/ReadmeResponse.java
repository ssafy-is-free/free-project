package com.ssafy.backend.domain.github.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadmeResponse {
	String readme;

	public static ReadmeResponse create(String readme) {
		return ReadmeResponse.builder().readme(readme).build();
	}

}
