package com.ssafy.backend.domain.github.controller;

import static com.ssafy.backend.global.response.CustomSuccessStatus.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.backend.PrincipalDetailsArgumentResolver;
import com.ssafy.backend.domain.github.dto.GitHubRankingFilter;
import com.ssafy.backend.domain.github.dto.GithubDetailResponse;
import com.ssafy.backend.domain.github.dto.GithubRankingCover;
import com.ssafy.backend.domain.github.dto.GithubRankingOneResponse;
import com.ssafy.backend.domain.github.dto.GithubRankingResponse;
import com.ssafy.backend.domain.github.dto.OpenRequest;
import com.ssafy.backend.domain.github.dto.ReadmeResponse;
import com.ssafy.backend.domain.github.service.GithubRankingService;
import com.ssafy.backend.domain.github.service.GithubService;
import com.ssafy.backend.domain.user.dto.NicknameListResponse;
import com.ssafy.backend.domain.util.service.NotificationManager;
import com.ssafy.backend.global.auth.filter.TokenAuthenticationFilter;
import com.ssafy.backend.global.config.sercurity.SecurityConfig;
import com.ssafy.backend.global.exception.ControllerAdvisor;
import com.ssafy.backend.global.response.CommonResponse;
import com.ssafy.backend.global.response.CustomSuccessStatus;
import com.ssafy.backend.global.response.DataResponse;
import com.ssafy.backend.global.response.ResponseService;
import com.ssafy.backend.global.response.ResponseStatus;

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
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("깃허브 랭크 테스트")
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
	@DisplayName("깃허브 리드미 테스트")
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
	@DisplayName("깃허브 유저 디테일 테스트")
	public void getGithubDetailsElseUserTest() throws Exception {
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
			get("/github/users/1")
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
	@DisplayName("깃허브 다른 유저 디테일 정보 테스트")
	public void getGithubMyDetailsElseUserTest() throws Exception {
		//given

		GithubDetailResponse githubDetailResponse = GithubDetailResponse.builder().build();
		given(githubService.getDetails(anyLong(), anyBoolean())).willReturn(githubDetailResponse);

		given(responseService.getDataResponse(githubDetailResponse, CustomSuccessStatus.RESPONSE_SUCCESS)).willReturn(
			getDataResponse(githubDetailResponse, RESPONSE_SUCCESS));

		//when
		ResultActions actions = mockMvc.perform(
			get("/github/users/2")
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
	@DisplayName("깃허브 나의 디테일 정보 테스트")
	public void getGithubMyDetailsTest() throws Exception {
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
			get("/github/users")
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
	@DisplayName("깃허브 닉네임 검색 테스트")
	public void getNicknameListTest() throws Exception {
		//given
		List<NicknameListResponse> nicknameListResponse = new ArrayList<>();
		nicknameListResponse.add(NicknameListResponse.builder().build());
		given(githubService.getNicknameList(anyString())).willReturn(nicknameListResponse);

		given(responseService.getDataResponse(nicknameListResponse, CustomSuccessStatus.RESPONSE_SUCCESS)).willReturn(
			getDataResponse(nicknameListResponse, RESPONSE_SUCCESS));

		//when
		ResultActions actions = mockMvc.perform(
			get("/github/search?nickname=nickname")
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
	@DisplayName("깃허브 닉네임 검색 Null 테스트")
	public void getNicknameListNullTest() throws Exception {
		//given

		given(githubService.getNicknameList(anyString())).willReturn(Collections.emptyList());
		given(
			responseService.getDataResponse(Collections.emptyList(), RESPONSE_NO_CONTENT)).willReturn(
			getDataResponse(Collections.emptyList(), RESPONSE_NO_CONTENT));

		//when
		ResultActions actions = mockMvc.perform(
			get("/github/search?nickname=nickname")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8"));

		//then
		actions
			.andDo(print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("조회된 데이터가 없습니다."));
	}

	@Test
	@DisplayName("깃허브 마이 랭크 테스트")
	public void getMyGithubRankTest() throws Exception {
		//given
		MockMvc mvc = MockMvcBuilders
			.standaloneSetup(new GithubController(responseService, githubService, githubRankingService))
			.setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver())
			.build();

		GithubRankingOneResponse githubRankingOneResponse = GithubRankingOneResponse.create(
			GithubRankingCover.builder().build());
		given(githubRankingService.getGithubRankOne(anyLong(), any(GitHubRankingFilter.class))).willReturn(
			githubRankingOneResponse);

		given(
			responseService.getDataResponse(githubRankingOneResponse, CustomSuccessStatus.RESPONSE_SUCCESS)).willReturn(
			getDataResponse(githubRankingOneResponse, RESPONSE_SUCCESS));

		//when
		ResultActions actions = mvc.perform(
			get("/github/my-rank?languageId=1&jobPostingId=1")
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
	@DisplayName("깃허브 마이 랭크 Null 테스트")
	public void getMyGithubRankNullTest() throws Exception {
		//given
		MockMvc mvc = MockMvcBuilders
			.standaloneSetup(new GithubController(responseService, githubService, githubRankingService))
			.setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver())
			.build();

		GithubRankingOneResponse githubRankingOneResponse = GithubRankingOneResponse.createEmpty();
		given(githubRankingService.getGithubRankOne(anyLong(), any(GitHubRankingFilter.class))).willReturn(
			githubRankingOneResponse);

		given(
			responseService.getDataResponse(Collections.emptyList(), RESPONSE_NO_CONTENT)).willReturn(
			getDataResponse(Collections.emptyList(), RESPONSE_NO_CONTENT));

		//when
		ResultActions actions = mvc.perform(
			get("/github/my-rank?languageId=1&jobPostingId=1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8"));

		//then
		actions
			.andDo(print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("조회된 데이터가 없습니다."));
	}

	@Test
	@DisplayName("깃허브 닉네임 검색 결과 테스트")
	public void searchGithubRankTest() throws Exception {
		//given
		GithubRankingOneResponse githubRankingOneResponse = GithubRankingOneResponse.create(
			GithubRankingCover.builder().build());
		given(githubRankingService.getGithubRankOne(anyLong(), any(GitHubRankingFilter.class))).willReturn(
			githubRankingOneResponse);

		given(
			responseService.getDataResponse(githubRankingOneResponse, CustomSuccessStatus.RESPONSE_SUCCESS)).willReturn(
			getDataResponse(githubRankingOneResponse, RESPONSE_SUCCESS));

		//when
		ResultActions actions = mockMvc.perform(
			get("/github/user-rank/1")
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
	@DisplayName("깃허브 닉네임 검색 결과 Null 테스트")
	public void searchGithubRankNullTest() throws Exception {
		//given
		GithubRankingOneResponse githubRankingOneResponse = GithubRankingOneResponse.createEmpty();
		given(githubRankingService.getGithubRankOne(anyLong(), any(GitHubRankingFilter.class))).willReturn(
			githubRankingOneResponse);

		given(
			responseService.getDataResponse(Collections.emptyList(), RESPONSE_NO_CONTENT)).willReturn(
			getDataResponse(Collections.emptyList(), RESPONSE_NO_CONTENT));

		//when
		ResultActions actions = mockMvc.perform(
			get("/github/user-rank/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8"));

		//then
		actions
			.andDo(print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("조회된 데이터가 없습니다."));
	}

	@Test
	@DisplayName("깃허브 공개/비공개 테스트")
	public void openGitRepositoryTest() throws Exception {
		//given
		MockMvc mvc = MockMvcBuilders
			.standaloneSetup(new GithubController(responseService, githubService, githubRankingService))
			.setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver())
			.build();

		given(responseService.getSuccessResponse()).willReturn(getSuccessResponse());

		OpenRequest openRequest = new OpenRequest();
		openRequest.setOpenStatus(true);
		openRequest.setGithubId(1L);

		//when
		ResultActions actions = mvc.perform(
			patch("/github/open")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(openRequest)));

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

	private CommonResponse getSuccessResponse() {
		CommonResponse response = new CommonResponse();
		response.setStatus(ResponseStatus.SUCCESS.toString());
		response.setMessage("요청에 성공했습니다.");
		return response;
	}
}
