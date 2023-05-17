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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.backend.PrincipalDetailsArgumentResolver;
import com.ssafy.backend.domain.EnableMockMvc;
import com.ssafy.backend.domain.user.dto.BojIdRequest;
import com.ssafy.backend.domain.user.service.BojService;
import com.ssafy.backend.domain.util.service.NotificationManager;
import com.ssafy.backend.global.auth.filter.TokenAuthenticationFilter;
import com.ssafy.backend.global.config.sercurity.SecurityConfig;
import com.ssafy.backend.global.exception.ControllerAdvisor;
import com.ssafy.backend.global.response.CommonResponse;
import com.ssafy.backend.global.response.ResponseService;
import com.ssafy.backend.global.response.ResponseStatus;

@WebMvcTest(controllers = BojController.class
	, excludeFilters = {@ComponentScan.Filter(
	type = FilterType.ASSIGNABLE_TYPE,
	classes = {SecurityConfig.class, TokenAuthenticationFilter.class, NotificationManager.class,
		ControllerAdvisor.class}
)
})
@EnableMockMvc
@WithMockUser
@DisplayName("유저 백준아이디 관련 테스트")
class BojControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BojService bojService;
	@MockBean
	private ResponseService responseService;

	@Test
	@DisplayName("백준 id 중복체크")
	void bojDuplicatedChecTest() throws Exception {
		//given

		String bojId = "testId";

		doNothing().when(bojService).checkDuplicateId(bojId);

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setStatus(String.valueOf(ResponseStatus.SUCCESS));
		commonResponse.setMessage(RESPONSE_SUCCESS.getMessage());

		given(responseService.getSuccessResponse()).willReturn(commonResponse);

		//when
		ResultActions actions = mockMvc.perform(get("/boj-id")
			.param("id", bojId)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));

		//then
		actions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("status", "SUCCESS").exists())
			.andExpect(jsonPath("message", RESPONSE_SUCCESS.getMessage()).exists());
	}

	@Test
	@DisplayName("백준 아이디 등록")
	void BojControllerTest() throws Exception {
		//given

		mockMvc = MockMvcBuilders
			.standaloneSetup(new BojController(bojService, responseService))
			.setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver())
			.build();

		String bojId = "updateId";
		BojIdRequest bojIdRequest = createBojIdRequest(bojId);

		doNothing().when(bojService).saveId(1L, bojIdRequest.getBojId());

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setStatus(String.valueOf(ResponseStatus.SUCCESS));
		commonResponse.setMessage(RESPONSE_SUCCESS.getMessage());

		given(responseService.getSuccessResponse()).willReturn(commonResponse);
		ObjectMapper objectMapper = new ObjectMapper();

		//when
		ResultActions actions = mockMvc.perform(patch("/boj-id")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8")
			.content(objectMapper.writeValueAsString(bojIdRequest)));

		//then
		actions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("status", "SUCCESS").exists())
			.andExpect(jsonPath("message", RESPONSE_SUCCESS.getMessage()).exists());

	}

	private BojIdRequest createBojIdRequest(String bojId) {
		BojIdRequest bojIdRequest = new BojIdRequest();
		bojIdRequest.setBojId(bojId);
		return bojIdRequest;
	}

}