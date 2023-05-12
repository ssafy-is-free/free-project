package com.ssafy.backend;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.global.auth.dto.UserPrincipal;

public class PrincipalDetailsArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().isAssignableFrom(UserPrincipal.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		User user = User.builder()
			.id(1L)
			.nickname("some name")
			.build();
		UserPrincipal userPrincipal = new UserPrincipal(user.getId(), user.getNickname(), true, true);
		return userPrincipal;
	}
}