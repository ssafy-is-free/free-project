package com.ssafy.backend.global.auth.handler;

import static com.ssafy.backend.global.auth.util.HttpCookieOAuth2AuthorizationRequestRepository.*;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.global.auth.dto.UserPrincipal;
import com.ssafy.backend.global.auth.util.CookieUtils;
import com.ssafy.backend.global.auth.util.HttpCookieOAuth2AuthorizationRequestRepository;
import com.ssafy.backend.global.auth.util.TokenProvider;
import com.ssafy.backend.global.config.properties.AuthProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// TODO: 2023-04-23 현재는 쿠키에 값을 그대로 담지만, httpcookieOuath2~~ 클래슬를 이용해서 인코딩하여 담는 식으로 수정 필요.
@Slf4j
@RequiredArgsConstructor
@Transactional
@Component
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final TokenProvider tokenProvider;
	private final UserRepository userRepository;
	private final AuthProperties authProperties;
	private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
	private static final int REFRESH_TOKEN_VALIDATE_TIME = 1000 * 60 * 60 * 24 * 7; // 1주일

	//로그인 성공후 동작하는 메서드
	// TODO: 2023-04-26 깃허브 정보를 가져오는 것을 여기서 처리하고 있지만, oauth 로그인이 된 직후에 같이 처리하도록 수정필요.
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		// //기존에 쿠키에 담겨있는 객체들 전부 삭제.
		// clearAuthenticationAttributes(request, response);

		//받은 유저 정보 객체 가져오기
		UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();

		//로그인에 성공하면 리프레시 토큰을 생성해서 디비에 저장함.
		String refreshToken = tokenProvider.createRefreshToken();
		saveRefreshToken(userPrincipal, refreshToken);

		//리다이렉트 할 경로 찾기 - 쿠키에 있으면 쿠키에서 꺼내고 없으면 서버에서 설정해둔 기본경로로 리다이렉트.
		String redirectUrl = findRedirectUrl(request, userPrincipal);

		// TODO: 2023-04-23 refresh 토큰 만료시간 프로퍼티 변수로 뺄 필요 있음 - 중복 사용
		// TODO: 2023-04-23 쿠키 만료시간을 리프레시 토큰 만료시간과 동일한 7일로 잡음 - 팀원들과 상의해서 만료시간 조정 필요
		//쿠키에 리프레시 토큰 담기.
		CookieUtils.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, refreshToken,
			REFRESH_TOKEN_VALIDATE_TIME);

		//해당 주소로 리다이렉트.
		getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}

	//리다이렉트 url 찾기
	public String findRedirectUrl(HttpServletRequest request, UserPrincipal userPrincipal) {

		//엑세스 쿠키 url에 붙여서 주기
		String accessToken = tokenProvider.createAccessToken(userPrincipal);
		String redirectUrl = authProperties.getRedirectPage();

		//        OAuth2AuthorizationRequest oAuth2AuthorizationRequest = httpCookieOAuth2AuthorizationRequestRepository.loadAuthorizationRequest(request);

		Optional<String> redirectUrlOptional = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
			.map(Cookie::getValue);

		//쿠키에 url이 있다면 해당 url 리턴.
		if (redirectUrlOptional.isPresent()) {
			// redirectUrl = redirectUrlOptional.get();
			redirectUrl = authProperties.getRedirectPageServer();
		}

		log.warn("redirect : {}", redirectUrl);
		return UriComponentsBuilder.fromUriString(redirectUrl)
			.queryParam("isNew", userPrincipal.isNew())
			.queryParam("token", accessToken)
			.build().toString();
	}

	//리프레시 토큰 저장 메서드
	public void saveRefreshToken(UserPrincipal userPrincipal, String refreshToken) {
		// TODO: 2023-04-23 표준 예외로 바꿔야됨.
		//유저 조회
		User user = userRepository.findByIdAndIsDeletedFalse(userPrincipal.getId())
			.orElseThrow(
				() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다  - 유저명 : " + userPrincipal.getUsername()));

		user.updateRefreshToken(refreshToken);
	}

	//기존에 있던 쿠키 값 삭제.
	protected void clearAuthenticationAttributes(HttpServletRequest request,
		HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);

		httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	}
}
