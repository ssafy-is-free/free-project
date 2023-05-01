package com.ssafy.backend.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomSuccessStatus {

	RESPONSE_SUCCESS(ResponseStatus.SUCCESS, "요청에 성공했습니다."),
	RESPONSE_NO_CONTENT(ResponseStatus.SUCCESS, "조회된 데이터가 없습니다.");

	private final ResponseStatus status;

	private final String message;
}
