package com.ssafy.backend.global.oauth.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Auth 제공자 enum 테스트")
class AuthProviderTest {

	@Test
	@DisplayName("존재하는 auth 제공자")
	void authSuccessTest() {
		//given
		String provider = "github";

		//when
		AuthProvider authProvider = AuthProvider.valueOf(provider);

		//then
		Assertions.assertThat(authProvider).isEqualTo(AuthProvider.github);

	}

	@Test
	@DisplayName("존재하지 않는 auth 제공자 테스트")
	void AuthProviderTest() {
		//given
		String provider = "boj";

		//when
		//then
		Assertions.assertThatCode(() -> AuthProvider.valueOf(provider))
			.isInstanceOf(IllegalArgumentException.class);

	}

}