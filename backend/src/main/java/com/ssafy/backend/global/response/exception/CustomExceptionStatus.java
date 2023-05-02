package com.ssafy.backend.global.response.exception;

import com.ssafy.backend.global.response.ResponseStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionStatus {

	/*유저*/
	NOT_FOUND_USER(ResponseStatus.FAIL, "해당하는 유저정보가 없습니다."), BOJ_DUPLICATED(ResponseStatus.FAIL, "이미 존재하는 아이디입니다."),
	NOT_FOUND_BOJ_USER(ResponseStatus.FAIL, "해당하는 백준 유저 정보가 없습니다."),

	/*인증 - 토큰 관련*/
	TOKEN_INVALID(ResponseStatus.FAIL, "유효하지 않은 토큰입니다."), TOKEN_EXPIRE(ResponseStatus.FAIL,
		"토큰이 만료되었습니다."), TOKEN_UNSUPPORTED(ResponseStatus.FAIL, "지원하지 않는 토큰입니다."), TOKEN_ILLEGAL(ResponseStatus.FAIL,
		"잘못된 토큰입니다."), TOKEN_NOT_FOUND(ResponseStatus.FAIL, "토큰이 없습니다."),

	/*리프레시 토큰 관련*/
	NOT_INVALID_REFRESH_TOKEN(ResponseStatus.FAIL, "유효하지 않은 리프레시 토큰입니다."),

	/*깃허브 관련*/
	NOT_FOUND_GITHUB(ResponseStatus.FAIL, "해당하는 유저의 깃허브 정보가 없습니다."),
	NOT_FOUND_REPOSITORY(ResponseStatus.FAIL, "해당하는 리포지토리 정보가 없습니다."), INCONSISTENT_GITHUB_ID(ResponseStatus.FAIL,
		"깃허브 아이디가 일치하지 않습니다."),

	/* 공고 관련*/
	NOT_FOUND_JOBPOSTING(ResponseStatus.FAIL, "해당하는 공고가 존재하지 않습니다.");

	private final ResponseStatus status;

	private final String message;
}
