package com.ssafy.backend.domain.user.service;

import static com.ssafy.backend.global.response.exception.CustomExceptionStatus.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.user.dto.AccessTokenResponse;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.global.auth.dto.UserPrincipal;
import com.ssafy.backend.global.auth.util.TokenProvider;
import com.ssafy.backend.global.response.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

	private final TokenProvider tokenProvider;
	private final UserRepository userRepository;

	@Override
	public AccessTokenResponse reissueToken(String oldAccessToken, String refreshToken) {

		//이전 토큰에서 id 값 꺼내기.
		Long userId = tokenProvider.getUserIdFromExpirationToken(oldAccessToken);

		//유저 id 조회
		User user = userRepository.findByIdAndIsDeletedFalse(userId)
			.orElseThrow(() -> new CustomException(NOT_FOUND_USER));

		//저장된 리프레시 토큰 검증 - 검증 실패하면, 예외 터트려서 프론트쪽에서 로그인 페이지로 넘어갈 수 있도록 해줌
		if (user.getRefreshToken() == null || !tokenProvider.validateRefreshToken(refreshToken, user.getRefreshToken()))
			throw new CustomException(NOT_INVALID_REFRESH_TOKEN);

		//토큰 생성후, DTO변환해서 리턴
		return AccessTokenResponse.create(tokenProvider.createAccessToken(UserPrincipal.createUserDetails(user)));
	}
}
