package com.ssafy.backend.domain.github.controller;

import static com.ssafy.backend.global.response.CustomSuccessStatus.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ssafy.backend.PrincipalDetailsArgumentResolver;
import com.ssafy.backend.domain.github.dto.GitHubRankingFilter;
import com.ssafy.backend.domain.github.dto.GithubDetailResponse;
import com.ssafy.backend.domain.github.dto.GithubRankingResponse;
import com.ssafy.backend.domain.github.dto.ReadmeResponse;
import com.ssafy.backend.domain.github.service.GithubRankingService;
import com.ssafy.backend.domain.github.service.GithubService;
import com.ssafy.backend.domain.util.service.NotificationManager;
import com.ssafy.backend.global.auth.filter.TokenAuthenticationFilter;
import com.ssafy.backend.global.config.sercurity.SecurityConfig;
import com.ssafy.backend.global.exception.ControllerAdvisor;
import com.ssafy.backend.global.response.CustomSuccessStatus;
import com.ssafy.backend.global.response.DataResponse;
import com.ssafy.backend.global.response.ResponseService;

@WebMvcTest(controllers = GithubController.class
	, excludeFilters = {@ComponentScan.Filter(
	type = FilterType.ASSIGNABLE_TYPE,
	classes = {SecurityConfig.class, TokenAuthenticationFilter.class, NotificationManager.class,
		ControllerAdvisor.class}
)
})
@WithMockUser
public class GithubControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ResponseService responseService;
	@MockBean
	private GithubService githubService;
	@MockBean
	private GithubRankingService githubRankingService;

	/*@Test
	@DisplayName("백준 랭크 테스트")
	public void getGithubRanksTest() throws Exception {
		//given
		GithubRankingResponse githubRankingResponse = GithubRankingResponse.createEmpty();
		*//*given(githubRankingService.getGithubRank(1L, 1L, 100,
			GitHubRankingFilter.builder().languageId(1L).jobPostingId(1L).build(),
			argThat(p -> p.getPageSize() == 20 && p.getPageNumber() == 0))).willReturn(
			githubRankingResponse);*//*
		given(githubRankingService.getGithubRank(1L, 1L, 100, GitHubRankingFilter.builder().languageId(1L).build(),
			null)).willReturn(githubRankingResponse);

		given(responseService.getDataResponse(githubRankingResponse, RESPONSE_SUCCESS)).willReturn(
			getDataResponse(githubRankingResponse, RESPONSE_SUCCESS));

		//when
		ResultActions actions = mockMvc.perform(
			get("/github/ranks?rank=1&userId=1&score=100&languageId=1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8"));

		//then
		actions
			.andDo(print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("요청에 성공했습니다."));
	}*/
	@Test
	@DisplayName("백준 랭크 테스트")
	public void getGithubRanksTest() throws Exception {
		//given
		GithubRankingResponse githubRankingResponse = GithubRankingResponse.createEmpty();
		given(githubRankingService.getGithubRank(anyLong(), anyLong(), anyInt(), any(GitHubRankingFilter.class),
			any(Pageable.class))).willReturn(githubRankingResponse);

		given(responseService.getDataResponse(githubRankingResponse, CustomSuccessStatus.RESPONSE_SUCCESS)).willReturn(
			getDataResponse(githubRankingResponse, RESPONSE_SUCCESS));

		//when
		ResultActions actions = mockMvc.perform(
			get("/github/ranks?rank=1&userId=1&score=100&languageId=1&size=20")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8"));

		//then
		actions
			.andDo(print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("요청에 성공했습니다."));
	}

	@Test
	@DisplayName("백준 랭크 테스트")
	public void getReadmeTest() throws Exception {
		//given
		ReadmeResponse readmeResponse = ReadmeResponse.builder().build();
		given(githubService.getReadme(anyLong(), anyLong())).willReturn(readmeResponse);

		given(responseService.getDataResponse(readmeResponse, CustomSuccessStatus.RESPONSE_SUCCESS)).willReturn(
			getDataResponse(readmeResponse, RESPONSE_SUCCESS));

		//when
		ResultActions actions = mockMvc.perform(
			get("/github/1/repositories/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8"));

		//then
		actions
			.andDo(print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("요청에 성공했습니다."));
	}

	@Test
	@DisplayName("백준 랭크 테스트")
	public void getGithubDetailsTest() throws Exception {
		//given
		MockMvc mvc = MockMvcBuilders
			.standaloneSetup(new GithubController(responseService, githubService, githubRankingService))
			.setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver())
			.build();

		GithubDetailResponse githubDetailResponse = GithubDetailResponse.builder().build();
		given(githubService.getDetails(anyLong(), anyBoolean())).willReturn(githubDetailResponse);

		given(responseService.getDataResponse(githubDetailResponse, CustomSuccessStatus.RESPONSE_SUCCESS)).willReturn(
			getDataResponse(githubDetailResponse, RESPONSE_SUCCESS));

		//when
		ResultActions actions = mvc.perform(
			get("/users/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8"));

		//then
		actions
			.andDo(print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("요청에 성공했습니다."));
	}

	private <T> DataResponse<T> getDataResponse(T data, CustomSuccessStatus status) {
		DataResponse<T> response = new DataResponse<>();
		response.setStatus(status.getStatus().toString());
		response.setMessage(status.getMessage());
		response.setData(data);

		return response;
	}
}
