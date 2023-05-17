package com.ssafy.backend.domain.user.controller;

import static com.ssafy.backend.global.response.CustomSuccessStatus.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ssafy.backend.domain.EnableMockMvc;
import com.ssafy.backend.domain.user.dto.AccessTokenResponse;
import com.ssafy.backend.domain.user.service.AuthService;
import com.ssafy.backend.domain.util.service.NotificationManager;
import com.ssafy.backend.global.auth.filter.TokenAuthenticationFilter;
import com.ssafy.backend.global.config.sercurity.SecurityConfig;
import com.ssafy.backend.global.exception.ControllerAdvisor;
import com.ssafy.backend.global.response.CustomSuccessStatus;
import com.ssafy.backend.global.response.DataResponse;
import com.ssafy.backend.global.response.ResponseService;

@WebMvcTest(controllers = AuthController.class
	, excludeFilters = {@ComponentScan.Filter(
	type = FilterType.ASSIGNABLE_TYPE,
	classes = {SecurityConfig.class, TokenAuthenticationFilter.class, NotificationManager.class,
		ControllerAdvisor.class}
)
})
@EnableMockMvc
@WithMockUser
@DisplayName("유저 인증 관련 테스트")
public class AuthControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private AuthService authService;
	@MockBean
	private ResponseService responseService;

	@Test
	@DisplayName("인증 컨트롤러 테스트")
	void reissueTokenTest() throws Exception {
		//given
		AccessTokenResponse accessTokenResponse = AccessTokenResponse.builder().build();

		given(authService.reissueToken("old", "refresh")).willReturn(accessTokenResponse);
		given(responseService.getDataResponse(accessTokenResponse, RESPONSE_SUCCESS)).willReturn(
			getDataResponse(accessTokenResponse, RESPONSE_SUCCESS));

		//when
		ResultActions actions = mockMvc.perform(get("/reissue")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));

		//then
		actions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("status", "SUCCESS").exists())
			.andExpect(jsonPath("message", RESPONSE_SUCCESS.getMessage()).exists());

	}

	private <T> DataResponse<T> getDataResponse(T data, CustomSuccessStatus status) {
		DataResponse<T> response = new DataResponse<>();
		response.setStatus(status.getStatus().toString());
		response.setMessage(status.getMessage());
		response.setData(data);

		return response;
	}

}
