package com.ssafy.backend.global.auth.util;

import java.util.Base64;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.SerializationUtils;

public class CookieUtils {

	public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return Optional.of(cookie);
				}
			}
		}
		return Optional.empty();
	}

	// TODO: 2023-04-23 테스트를 위해서 secure를 잠시 꺼둠
	// TODO: 2023-04-23 테스트시에는 samesite 기능을 켜줘야 함, servlet에서 제공하는 쿠키는 이 기능이 없어서
	// TODO: 2023-04-23 response에 직접 헤더를 추가해줘야함. - 아니면 스프링이 제공하는 reponseCookie를 사용해야됨.
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setSecure(false); //https로 통신할때만 웹 브라우저가 쿠키를 서버로 전송하도록 함.
		cookie.setPath("/");
		cookie.setHttpOnly(true); //쿠키를 클라이언트에서 열어볼 수 없도록 함.
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					cookie.setValue("");
					cookie.setPath("/");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
	}

	public static String serialize(Object object) {
		return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(object));
	}

	public static <T> T deserialize(Cookie cookie, Class<T> cls) {
		return cls.cast(SerializationUtils.deserialize(Base64.getUrlDecoder().decode(cookie.getValue())));
	}
}
