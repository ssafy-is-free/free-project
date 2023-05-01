package com.ssafy.backend.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AccessTokenResponse {

	@JsonProperty("access-token")
	private String accessToken;

	public static AccessTokenResponse create(String accessToken) {

		return AccessTokenResponse.builder()
			.accessToken(accessToken)
			.build();
	}
}
