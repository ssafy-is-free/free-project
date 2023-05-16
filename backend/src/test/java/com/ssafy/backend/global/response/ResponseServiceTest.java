package com.ssafy.backend.global.response;

import static com.ssafy.backend.global.response.CustomSuccessStatus.*;
import static com.ssafy.backend.global.response.exception.CustomExceptionStatus.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ResponseServiceTest {

	@Autowired
	private ResponseService responseService;

	@Test
	@DisplayName("요청 성공 테스트 -응답 데이터가 없는 경우")
	public void getSuccessResponseTest() {
		//given & when
		CommonResponse response = responseService.getSuccessResponse();

		//then
		assertThat(response).extracting(CommonResponse::getStatus,
			CommonResponse::getMessage).containsExactly(ResponseStatus.SUCCESS.toString(), "요청에 성공했습니다.");
	}

	@Test
	@DisplayName("요청 성공 테스트 -응답 데이터가 있는 경우 -컨텐츠가 있는 경우")
	public void getDataResponseSuccessTest() {
		//given
		TestGeneric testGeneric = new TestGeneric("Test");

		//when
		DataResponse<TestGeneric> response = responseService.getDataResponse(testGeneric, RESPONSE_SUCCESS);

		//then
		assertThat(response).extracting(DataResponse -> DataResponse.getData().getName(),
				DataResponse::getStatus,
				DataResponse::getMessage)
			.containsExactly("Test", ResponseStatus.SUCCESS.toString(), "요청에 성공했습니다.");
	}

	@Test
	@DisplayName("요청 성공 테스트 -응답 데이터가 있는 경우 -비어있는 컨텐츠의 경우")
	public void getDataResponseTest() {
		//given
		TestGeneric testGeneric = new TestGeneric("Test");

		//when
		CommonResponse response = responseService.getDataResponse(testGeneric, RESPONSE_NO_CONTENT);

		//then
		assertThat(response).extracting(CommonResponse::getStatus,
				CommonResponse::getMessage)
			.containsExactly(ResponseStatus.SUCCESS.toString(), "조회된 데이터가 없습니다.");
	}

	@Test
	@DisplayName("예외 응답 테스트")
	public void getExceptionResponseTest() {
		//given & when
		List<CommonResponse> response = new ArrayList<>();
		response.add(responseService.getExceptionResponse(NOT_FOUND_USER));
		response.add(responseService.getExceptionResponse(BOJ_DUPLICATED));
		response.add(responseService.getExceptionResponse(NOT_FOUND_BOJ_USER));
		response.add(responseService.getExceptionResponse(TOKEN_INVALID));
		response.add(responseService.getExceptionResponse(TOKEN_EXPIRE));
		response.add(responseService.getExceptionResponse(TOKEN_UNSUPPORTED));
		response.add(responseService.getExceptionResponse(TOKEN_ILLEGAL));
		response.add(responseService.getExceptionResponse(TOKEN_NOT_FOUND));
		response.add(responseService.getExceptionResponse(NOT_INVALID_REFRESH_TOKEN));
		response.add(responseService.getExceptionResponse(NOT_FOUND_GITHUB));
		response.add(responseService.getExceptionResponse(INCONSISTENT_GITHUB_ID));
		response.add(responseService.getExceptionResponse(NOT_FOUND_JOBPOSTING));
		response.add(responseService.getExceptionResponse(NOT_APPLY));
		response.add(responseService.getExceptionResponse(NOT_FOUND_JOBHISTORY));
		response.add(responseService.getExceptionResponse(NOT_FOUND_APPLICANT));
		response.add(responseService.getExceptionResponse(NOT_FOUND_REPOSITORY));

		//then
		assertThat(response).extracting(CommonResponse::getStatus,
				CommonResponse::getMessage)
			.containsExactly(tuple("FAIL", "해당하는 유저정보가 없습니다."),
				tuple("FAIL", "이미 존재하는 아이디입니다."),
				tuple("FAIL", "해당하는 백준 유저 정보가 없습니다."),
				tuple("FAIL", "유효하지 않은 토큰입니다."),
				tuple("FAIL", "토큰이 만료되었습니다."),
				tuple("FAIL", "지원하지 않는 토큰입니다."),
				tuple("FAIL", "잘못된 토큰입니다."),
				tuple("FAIL", "토큰이 없습니다."),
				tuple("FAIL", "유효하지 않은 리프레시 토큰입니다."),
				tuple("FAIL", "해당하는 유저의 깃허브 정보가 없습니다."),
				tuple("FAIL", "깃허브 아이디가 일치하지 않습니다."),
				tuple("FAIL", "해당하는 공고가 존재하지 않습니다."),
				tuple("FAIL", "해당 공고에 지원하지 않은 사용자는 접근할 수 없습니다."),
				tuple("FAIL", "해당하는 이력이 없습니다."), tuple("FAIL", "해당하는 공고의 지원자를 찾을 수 없습니다."),
				tuple("FAIL", "해당하는 리포지토리 정보가 없습니다.")
			);
	}

	private static class TestGeneric {
		String name;

		public String getName() {
			return this.name;
		}

		public TestGeneric(String name) {
			this.name = name;
		}

	}
}
