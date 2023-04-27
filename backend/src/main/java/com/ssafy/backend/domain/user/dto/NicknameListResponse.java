package com.ssafy.backend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class NicknameListResponse {

	private long userId;
	private String nickname;

	public static NicknameListResponse create(long userId, String nickname) {
		return NicknameListResponse.builder()
			.userId(userId)
			.nickname(nickname)
			.build();
	}
}
