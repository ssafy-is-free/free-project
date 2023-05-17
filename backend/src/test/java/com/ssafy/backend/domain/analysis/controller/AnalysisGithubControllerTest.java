package com.ssafy.backend.domain.analysis.controller;

import static com.ssafy.backend.global.response.CustomSuccessStatus.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
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

import com.ssafy.backend.PrincipalDetailsArgumentResolver;
import com.ssafy.backend.domain.analysis.dto.response.CompareGithubResponse;
import com.ssafy.backend.domain.analysis.dto.response.GithubVsInfo;
import com.ssafy.backend.domain.analysis.service.AnalysisGithubService;
import com.ssafy.backend.domain.util.service.NotificationManager;
import com.ssafy.backend.global.auth.filter.TokenAuthenticationFilter;
import com.ssafy.backend.global.config.sercurity.SecurityConfig;
import com.ssafy.backend.global.exception.ControllerAdvisor;
import com.ssafy.backend.global.response.CustomSuccessStatus;
import com.ssafy.backend.global.response.DataResponse;
import com.ssafy.backend.global.response.ResponseService;

@WebMvcTest(controllers = AnalysisGithubController.class
	, excludeFilters = {@ComponentScan.Filter(
	type = FilterType.ASSIGNABLE_TYPE,
	classes = {SecurityConfig.class, TokenAuthenticationFilter.class, NotificationManager.class,
		ControllerAdvisor.class}
)
})
@WithMockUser
public class AnalysisGithubControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private AnalysisGithubService analysisGithubService;
	@MockBean
	private ResponseService responseService;

	@BeforeEach
	void before() {
		mockMvc = MockMvcBuilders
			.standaloneSetup(new AnalysisGithubController(responseService, analysisGithubService))
			.setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver())
			.build();
	}

	@Test
	@DisplayName("깃허브 1대1 비교 테스트")
	public void githubCompareWithOpponentTest() throws Exception {
		//given
		CompareGithubResponse compareGithubResponse = CompareGithubResponse.builder()
			.my(GithubVsInfo.builder().build())
			.opponent(GithubVsInfo.builder().build())
			.build();

		given(analysisGithubService.compareWithOpponent(2L, 1L)).willReturn(compareGithubResponse);
		given(responseService.getDataResponse(compareGithubResponse, RESPONSE_SUCCESS)).willReturn(
			getDataResponse(compareGithubResponse, RESPONSE_SUCCESS));
		//when
		ResultActions actions = mockMvc.perform(get("/analysis/github/users/2")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));

		//then
		actions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value(RESPONSE_SUCCESS.getMessage()));

	}

	@Test
	@DisplayName("깃허브 1대N 비교 테스트")
	public void githubCompareWithOtherTest() throws Exception {
		//given
		CompareGithubResponse compareGithubResponse = CompareGithubResponse.builder()
			.my(GithubVsInfo.builder().build())
			.opponent(GithubVsInfo.builder().build())
			.build();

		given(analysisGithubService.compareWithAllApplicant(1L, 1L)).willReturn(compareGithubResponse);
		given(responseService.getDataResponse(compareGithubResponse, RESPONSE_SUCCESS)).willReturn(
			getDataResponse(compareGithubResponse, RESPONSE_SUCCESS));
		//when
		ResultActions actions = mockMvc.perform(get("/analysis/github/postings/1")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));

		//then
		actions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value(RESPONSE_SUCCESS.getMessage()));

	}

	private <T> DataResponse<T> getDataResponse(T data, CustomSuccessStatus status) {
		DataResponse<T> response = new DataResponse<>();
		response.setStatus(status.getStatus().toString());
		response.setMessage(status.getMessage());
		response.setData(data);

		return response;
	}
}
