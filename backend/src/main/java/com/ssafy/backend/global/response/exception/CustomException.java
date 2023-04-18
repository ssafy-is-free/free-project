package com.ssafy.backend.global.response.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{

    CustomExceptionStatus customExceptionStatus;
}
