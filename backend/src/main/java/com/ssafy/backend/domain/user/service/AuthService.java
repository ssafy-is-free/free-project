package com.ssafy.backend.domain.user.service;

import com.ssafy.backend.domain.user.dto.AccessTokenResponse;

public interface AuthService {

	//엑세스 토큰 재발행.
	AccessTokenResponse reissueToken(String oldAccessToken, String refreshToken);
}
