package com.ssafy.backend.global.exception;

import static com.ssafy.backend.global.response.ResponseStatus.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.backend.global.response.CommonResponse;
import com.ssafy.backend.global.response.ResponseService;
import com.ssafy.backend.global.response.exception.CustomException;
import com.ssafy.backend.global.response.exception.CustomExceptionStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
@RestControllerAdvice
public class ControllerAdvisor {

	private final ResponseService responseService;

	@ExceptionHandler
	//커스텀 예외 처리 - 커스텀 예외를 먼저 처리하도록 둠.
	public CommonResponse exceptionHandler(CustomException e) {

		CustomExceptionStatus status = e.getCustomExceptionStatus();

		e.printStackTrace();
		log.warn(
			"[" + " CustomException - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))
				+ "]" + " : " + status.getMessage());

		return responseService.getExceptionResponse(status);

	}

	//커스텀 예외로 확인되지 않은 모든 예외
	@ExceptionHandler
	public CommonResponse exceptionHandler(Exception e) {

		//        e.printStackTrace();

		log.error(
			"[" + " Exception - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")) + "]"
				+ " : " + e.getMessage());

		CommonResponse response = new CommonResponse();
		response.setStatus(ERROR.toString());
		response.setMessage(e.getMessage());

		return response;
	}
}
