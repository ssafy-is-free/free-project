package com.ssafy.backend.domain.user.controller;

import static com.ssafy.backend.global.auth.util.HttpCookieOAuth2AuthorizationRequestRepository.*;
import static com.ssafy.backend.global.response.CustomSuccessStatus.*;
import static com.ssafy.backend.global.response.exception.CustomExceptionStatus.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.domain.user.dto.AccessTokenResponse;
import com.ssafy.backend.domain.user.service.AuthService;
import com.ssafy.backend.global.auth.util.CookieUtils;
import com.ssafy.backend.global.response.DataResponse;
import com.ssafy.backend.global.response.ResponseService;
import com.ssafy.backend.global.response.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class AuthController {

	private final AuthService authService;
	private final ResponseService responseService;

	@GetMapping("reissue")
	public DataResponse<AccessTokenResponse> reissueToken(
		@RequestHeader("Authorization") String accessToken,
		HttpServletRequest request) {

		//토큰이 형식에 맞지 않거나 데이터가 없으면.
		if (!StringUtils.hasText(accessToken) || !accessToken.startsWith("Bearer ")) {
			throw new CustomException(TOKEN_ILLEGAL);
		}

		//이전 엑세스 토큰 추출
		String oldAccessToken = accessToken.substring(7);

		//쿠키에서 리프레시 토큰 꺼내기
		Cookie cookie = CookieUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
			.orElseThrow(() -> new CustomException(NOT_INVALID_REFRESH_TOKEN));

		String refreshToken = cookie.getValue();

		//리프레시 토큰확인하고 엑세스 토큰 재발급.
		AccessTokenResponse accessTokenResponse = authService.reissueToken(oldAccessToken, refreshToken);

		return responseService.getDataResponse(accessTokenResponse, RESPONSE_SUCCESS);
	}
}
