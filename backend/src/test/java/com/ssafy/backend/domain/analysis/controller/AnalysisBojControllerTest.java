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
import com.ssafy.backend.domain.algorithm.dto.response.BojInfoDetailResponse;
import com.ssafy.backend.domain.analysis.dto.BojRankAllComparisonResponse;
import com.ssafy.backend.domain.analysis.dto.BojRankComparisonResponse;
import com.ssafy.backend.domain.analysis.dto.response.BojInfoAvgDetailResponse;
import com.ssafy.backend.domain.analysis.service.AnalysisBojService;
import com.ssafy.backend.domain.util.service.NotificationManager;
import com.ssafy.backend.global.auth.filter.TokenAuthenticationFilter;
import com.ssafy.backend.global.config.sercurity.SecurityConfig;
import com.ssafy.backend.global.exception.ControllerAdvisor;
import com.ssafy.backend.global.response.CustomSuccessStatus;
import com.ssafy.backend.global.response.DataResponse;
import com.ssafy.backend.global.response.ResponseService;

@WebMvcTest(controllers = AnalysisBojController.class
	, excludeFilters = {@ComponentScan.Filter(
	type = FilterType.ASSIGNABLE_TYPE,
	classes = {SecurityConfig.class, TokenAuthenticationFilter.class, NotificationManager.class,
		ControllerAdvisor.class}
)
})
@WithMockUser
public class AnalysisBojControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private AnalysisBojService analysisBojService;
	@MockBean
	private ResponseService responseService;

	@BeforeEach
	void before() {
		mockMvc = MockMvcBuilders
			.standaloneSetup(new AnalysisBojController(analysisBojService, responseService))
			.setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver())
			.build();
	}

	@Test
	@DisplayName("백준 1대1 비교 테스트")
	public void bojCompareWithOpponentTest() throws Exception {
		//given
		BojRankComparisonResponse bojRankComparisonResponse = BojRankComparisonResponse.builder()
			.my(BojInfoDetailResponse.createEmpty())
			.opponent(BojInfoDetailResponse.createEmpty())
			.build();

		given(analysisBojService.compareWithOpponent(1L, 2L)).willReturn(bojRankComparisonResponse);
		given(responseService.getDataResponse(bojRankComparisonResponse, RESPONSE_SUCCESS)).willReturn(
			getDataResponse(bojRankComparisonResponse, RESPONSE_SUCCESS));
		//when
		ResultActions actions = mockMvc.perform(get("/analysis/boj/users/2")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));

		//then
		actions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("요청에 성공했습니다."));

	}

	@Test
	@DisplayName("백준 1대1 비교 Null 테스트")
	public void bojCompareWithOpponentNullTest() throws Exception {
		//given
		BojRankComparisonResponse bojRankComparisonResponse = BojRankComparisonResponse.builder()
			.my(null)
			.opponent(null)
			.build();

		given(analysisBojService.compareWithOpponent(1L, 2L)).willReturn(bojRankComparisonResponse);
		given(responseService.getDataResponse(null, RESPONSE_NO_CONTENT)).willReturn(
			getDataResponse(bojRankComparisonResponse, RESPONSE_NO_CONTENT));
		//when
		ResultActions actions = mockMvc.perform(get("/analysis/boj/users/2")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));

		//then
		actions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value(RESPONSE_NO_CONTENT.getMessage()));

	}

	@Test
	@DisplayName("백준 1대N 비교 테스트")
	public void bojCompareWithOtherTest() throws Exception {
		//given
		BojRankAllComparisonResponse bojRankAllComparisonResponse = BojRankAllComparisonResponse.builder()
			.my(new BojInfoDetailResponse())
			.other(new BojInfoAvgDetailResponse())
			.build();

		given(analysisBojService.compareWithOther(1L, 1L)).willReturn(bojRankAllComparisonResponse);
		given(responseService.getDataResponse(bojRankAllComparisonResponse, RESPONSE_SUCCESS)).willReturn(
			getDataResponse(bojRankAllComparisonResponse, RESPONSE_SUCCESS));
		//when
		ResultActions actions = mockMvc.perform(get("/analysis/boj/postings/1")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));

		//then
		actions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("요청에 성공했습니다."));

	}

	@Test
	@DisplayName("백준 1대N 비교 Null 테스트")
	public void bojCompareWithOtherNullTest() throws Exception {
		//given
		BojRankAllComparisonResponse bojRankAllComparisonResponse = BojRankAllComparisonResponse.builder()
			.my(null)
			.other(null)
			.build();

		given(analysisBojService.compareWithOther(1L, 1L)).willReturn(bojRankAllComparisonResponse);
		given(responseService.getDataResponse(null, RESPONSE_NO_CONTENT)).willReturn(
			getDataResponse(bojRankAllComparisonResponse, RESPONSE_NO_CONTENT));
		//when
		ResultActions actions = mockMvc.perform(get("/analysis/boj/postings/1")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));

		//then
		actions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value(RESPONSE_NO_CONTENT.getMessage()));

	}

	private <T> DataResponse<T> getDataResponse(T data, CustomSuccessStatus status) {
		DataResponse<T> response = new DataResponse<>();
		response.setStatus(status.getStatus().toString());
		response.setMessage(status.getMessage());
		response.setData(data);

		return response;
	}
}
