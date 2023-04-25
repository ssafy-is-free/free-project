package com.ssafy.backend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BojIdListResponseDTO {
	private long userId;
	private String bojId;

	public static BojIdListResponseDTO create(long userId, String bojId) {
		return BojIdListResponseDTO.builder()
			.userId(userId)
			.bojId(bojId)
			.build();
	}

}
