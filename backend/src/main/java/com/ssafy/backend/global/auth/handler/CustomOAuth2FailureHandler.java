package com.ssafy.backend.global.auth.handler;

import static com.ssafy.backend.global.auth.util.HttpCookieOAuth2AuthorizationRequestRepository.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.ssafy.backend.global.auth.util.CookieUtils;
import com.ssafy.backend.global.auth.util.HttpCookieOAuth2AuthorizationRequestRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomOAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

	// TODO: 2023-04-23 리다이렉트 주소를 예외 페이지(프론트쪽에서 만들었다면,)로 변경해야됨
	// TODO: 2023-04-23 쿠키에서 직접 꺼내지 말고 httpCookieOAuth~~ 클래스를 이용해서 꺼내도록 해야됨.
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {

		String redirectUrl = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
			.map(Cookie::getValue)
			.orElse(("/"));

		httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
		//url에 에러메시지 붙여서 리다이렉트.
		redirectUrl = UriComponentsBuilder.fromUriString(redirectUrl)
			.queryParam("token", "")
			.queryParam("error", exception.getLocalizedMessage())
			.build().toUriString();

		getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}
}
