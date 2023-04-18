package com.ssafy.backend.global.response.exception;


import com.ssafy.backend.global.response.ResponseStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionStatus {

    NOT_FOUND_USER(ResponseStatus.FAIL, "해당하는 유저정보가 없습니다."),


    ;
    private final ResponseStatus status;

    private final String message;
}
