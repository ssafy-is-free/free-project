package com.ssafy.backend.global.response.exception;


import com.ssafy.backend.global.response.ResponseStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionStatus {

    NOT_FOUND_USER(ResponseStatus.FAIL, "해당하는 유저정보가 없습니다."),

    /*인증 - 토큰 관련*/
    TOKEN_INVALID(ResponseStatus.FAIL,"유효하지 않은 토큰입니다."),
    TOKEN_EXPIRE(ResponseStatus.FAIL,"토큰이 만료되었습니다."),
    TOKEN_UNSUPPORTED(ResponseStatus.FAIL,"지원하지 않는 토큰입니다."),
    TOKEN_ILLEGAL(ResponseStatus.FAIL,"잘못된 토큰입니다."),
    TOKEN_NOT_FOUND(ResponseStatus.FAIL, "토큰이 없습니다."),

    /*리프레시 토큰 관련*/
    NOT_INVALID_REFRESH_TOKEN(ResponseStatus.FAIL, "유효하지 않은 리프레시 토큰입니다."),


    ;
    private final ResponseStatus status;

    private final String message;
}
