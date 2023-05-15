package com.ssafy.backend.domain.algorithm.controller;

import static com.ssafy.backend.global.response.CustomSuccessStatus.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
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

import com.ssafy.backend.PrincipalDetailsArgumentResolver;
import com.ssafy.backend.domain.algorithm.dto.response.BojInfoDetailResponse;
import com.ssafy.backend.domain.algorithm.dto.response.BojRankResponse;
import com.ssafy.backend.domain.algorithm.service.AlgorithmService;
import com.ssafy.backend.domain.user.dto.NicknameListResponse;
import com.ssafy.backend.domain.util.service.NotificationManager;
import com.ssafy.backend.global.auth.filter.TokenAuthenticationFilter;
import com.ssafy.backend.global.config.sercurity.SecurityConfig;
import com.ssafy.backend.global.exception.ControllerAdvisor;
import com.ssafy.backend.global.response.CustomSuccessStatus;
import com.ssafy.backend.global.response.DataResponse;
import com.ssafy.backend.global.response.ResponseService;

@WebMvcTest(controllers = AlgorithmController.class
	, excludeFilters = {@ComponentScan.Filter(
	type = FilterType.ASSIGNABLE_TYPE,
	classes = {SecurityConfig.class, TokenAuthenticationFilter.class, NotificationManager.class,
		ControllerAdvisor.class}
)
})
@WithMockUser
public class AlgorithmControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private AlgorithmService algorithmService;
	@MockBean
	private ResponseService responseService;

	@Test
	@DisplayName("백준 마이랭크 테스트")
	public void bojMyRankTest() throws Exception {

		MockMvc mvc = MockMvcBuilders
			.standaloneSetup(new AlgorithmController(responseService, algorithmService))
			.setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver())
			.build();
		//given
		BojRankResponse bojRankResponse = BojRankResponse.builder()
			.userId(1L)
			.rank(1)
			.score(100)
			.nickname("Nickname")
			.avatarUrl("1")
			.tierUrl("14")
			.rankUpDown(2L)
			.build();

		given(algorithmService.getBojByUserId(1L, null, null)).willReturn(bojRankResponse);
		given(responseService.getDataResponse(bojRankResponse, RESPONSE_SUCCESS)).willReturn(
			getDataResponse(bojRankResponse, RESPONSE_SUCCESS));
		//when
		ResultActions actions = mvc.perform(get("/boj/my-rank")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));
		//then
		actions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.nickname").value("Nickname"))
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("요청에 성공했습니다."));

	}

	@Test
	@DisplayName("백준 마이랭크 실패 테스트")
	public void bojMyRankFailTest() throws Exception {

		MockMvc mvc = MockMvcBuilders
			.standaloneSetup(new AlgorithmController(responseService, algorithmService))
			.setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver())
			.build();
		//given
		BojRankResponse bojRankResponse = new BojRankResponse();

		given(algorithmService.getBojByUserId(1L, null, null)).willReturn(bojRankResponse);
		given(responseService.getDataResponse(null, RESPONSE_NO_CONTENT)).willReturn(
			getDataResponse(bojRankResponse, RESPONSE_NO_CONTENT));
		//when
		ResultActions actions = mvc.perform(get("/boj/my-rank")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));
		//then
		actions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("조회된 데이터가 없습니다."));

	}

	@Test
	@DisplayName("백준 상세정보 userId 입력 테스트")
	public void getBojInfoDetailTest() throws Exception {

		MockMvc mvc = MockMvcBuilders
			.standaloneSetup(new AlgorithmController(responseService, algorithmService))
			.setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver())
			.build();
		//given
		BojInfoDetailResponse bojInfoDetailResponse = BojInfoDetailResponse.builder()
			.bojId("soda")
			.tierUrl("tier")
			.fail(10)
			.pass(10)
			.submit(20)
			.tryFail(5)
			.languages(new ArrayList<>())
			.build();

		given(algorithmService.getBojInfoDetailByUserId(1L)).willReturn(bojInfoDetailResponse);
		given(responseService.getDataResponse(bojInfoDetailResponse, RESPONSE_SUCCESS)).willReturn(
			getDataResponse(bojInfoDetailResponse, RESPONSE_SUCCESS));
		//when
		ResultActions actions = mvc.perform(get("/boj/users/1")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));
		//then
		actions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.bojId").value("soda"))
			.andExpect(jsonPath("$.data.tierUrl").value("tier"))
			.andExpect(jsonPath("$.data.pass").value(10))
			.andExpect(jsonPath("$.data.tryFail").value(5))
			.andExpect(jsonPath("$.data.submit").value(20))
			.andExpect(jsonPath("$.data.fail").value(10))
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("요청에 성공했습니다."));

	}

	@Test
	@DisplayName("백준 본인 상세정보 테스트")
	public void getBojInfoMyDetailTest() throws Exception {

		MockMvc mvc = MockMvcBuilders
			.standaloneSetup(new AlgorithmController(responseService, algorithmService))
			.setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver())
			.build();
		//given
		BojInfoDetailResponse bojInfoDetailResponse = BojInfoDetailResponse.builder()
			.bojId("soda")
			.tierUrl("tier")
			.fail(10)
			.pass(10)
			.submit(20)
			.tryFail(5)
			.languages(new ArrayList<>())
			.build();

		given(algorithmService.getBojInfoDetailByUserId(1L)).willReturn(bojInfoDetailResponse);
		given(responseService.getDataResponse(bojInfoDetailResponse, RESPONSE_SUCCESS)).willReturn(
			getDataResponse(bojInfoDetailResponse, RESPONSE_SUCCESS));
		//when
		ResultActions actions = mvc.perform(get("/boj/users")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));
		//then
		actions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.bojId").value("soda"))
			.andExpect(jsonPath("$.data.tierUrl").value("tier"))
			.andExpect(jsonPath("$.data.pass").value(10))
			.andExpect(jsonPath("$.data.tryFail").value(5))
			.andExpect(jsonPath("$.data.submit").value(20))
			.andExpect(jsonPath("$.data.fail").value(10))
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("요청에 성공했습니다."));

	}

	@Test
	@DisplayName("백준 상세정보 비어있을 때 테스트")
	public void getBojInfoDetailNullTest() throws Exception {

		MockMvc mvc = MockMvcBuilders
			.standaloneSetup(new AlgorithmController(responseService, algorithmService))
			.setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver())
			.build();
		//given
		BojInfoDetailResponse bojInfoDetailResponse = new BojInfoDetailResponse();

		given(algorithmService.getBojInfoDetailByUserId(1L)).willReturn(bojInfoDetailResponse);
		given(responseService.getDataResponse(null, RESPONSE_NO_CONTENT)).willReturn(
			getDataResponse(bojInfoDetailResponse, RESPONSE_NO_CONTENT));
		//when
		ResultActions actions = mvc.perform(get("/boj/users")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));
		//then
		actions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("조회된 데이터가 없습니다."));

	}

	@Test
	@DisplayName("백준 닉네임 자동완성 테스트")
	public void getBojIdListTest() throws Exception {
		//given
		List<NicknameListResponse> nicknameListResponseList = new ArrayList<>();
		nicknameListResponseList.add(new NicknameListResponse(1L, "sodamito"));

		given(algorithmService.getBojListByBojId(anyString())).willReturn(nicknameListResponseList);
		given(responseService.getDataResponse(nicknameListResponseList, RESPONSE_SUCCESS)).willReturn(
			getDataResponse(nicknameListResponseList, RESPONSE_SUCCESS));
		//when
		ResultActions actions = mockMvc.perform(get("/boj/search?nickname=soda")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));
		//then
		actions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data[0].nickname").value("sodamito"))
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("요청에 성공했습니다."));

	}

	@Test
	@DisplayName("백준 닉네임 자동완성 없을 경우 테스트")
	public void getBojIdListNicknameNullTest() throws Exception {
		//given
		List<NicknameListResponse> nicknameListResponseList = new ArrayList<>();

		given(algorithmService.getBojListByBojId(anyString())).willReturn(nicknameListResponseList);
		given(responseService.getDataResponse(nicknameListResponseList, RESPONSE_NO_CONTENT)).willReturn(
			getDataResponse(nicknameListResponseList, RESPONSE_NO_CONTENT));
		//when
		ResultActions actions = mockMvc.perform(get("/boj/search?nickname=soda")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));
		//then
		actions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data", hasSize(0)))
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("조회된 데이터가 없습니다."));

	}

	@Test
	@DisplayName("백준 닉네임 완성 결과 테스트")
	public void getBojIdTest() throws Exception {
		//given

		BojRankResponse bojRankResponse = BojRankResponse.builder()
			.rank(1)
			.rankUpDown(0L)
			.tierUrl("Test14")
			.userId(1L)
			.nickname("user1")
			.build();

		given(algorithmService.getBojByUserId(1L, 1L, null)).willReturn(bojRankResponse);
		given(responseService.getDataResponse(bojRankResponse, RESPONSE_SUCCESS)).willReturn(
			getDataResponse(bojRankResponse, RESPONSE_SUCCESS));
		//when
		ResultActions actions = mockMvc.perform(get("/boj/user-rank/1?languageId=1")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));
		//then
		actions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.rank").value(1))
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("요청에 성공했습니다."));

	}

	@Test
	@DisplayName("백준 닉네임 완성 결과 없을 때 테스트")
	public void getBojIdNullTest() throws Exception {
		//given

		BojRankResponse bojRankResponse = BojRankResponse.builder()
			.build();

		given(algorithmService.getBojByUserId(1L, 1L, null)).willReturn(bojRankResponse);
		given(responseService.getDataResponse(null, RESPONSE_NO_CONTENT)).willReturn(
			getDataResponse(bojRankResponse, RESPONSE_NO_CONTENT));
		//when
		ResultActions actions = mockMvc.perform(get("/boj/user-rank/1?languageId=1")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));
		//then
		actions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.userId").isEmpty())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("조회된 데이터가 없습니다."));

	}

	@Test
	@DisplayName("백준 랭크 테스트")
	public void getBojRankTest() throws Exception {
		//given
		List<BojRankResponse> bojRankResponseList = new ArrayList<>();
		bojRankResponseList.add(
			BojRankResponse.builder().rank(1).rankUpDown(0L).tierUrl("Test14").userId(1L).nickname("user1").build());
		given(algorithmService.getBojRankListByBojId(null, null, null, null, null, null,
			Pageable.ofSize(20))).willReturn(bojRankResponseList);
		given(responseService.getDataResponse(bojRankResponseList, RESPONSE_SUCCESS)).willReturn(
			getDataResponse(bojRankResponseList, RESPONSE_SUCCESS));

		//when
		ResultActions actions = mockMvc.perform(get("/boj/ranks")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));

		//then
		actions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.data", hasSize(1)))
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("요청에 성공했습니다."));
	}

	@Test
	@DisplayName("백준 랭크 빈값 테스트")
	public void getBojRankFailTest() throws Exception {
		//given
		List<BojRankResponse> bojRankResponseList = new ArrayList<>();
		given(algorithmService.getBojRankListByBojId(null, null, null, null, null, null,
			Pageable.ofSize(20))).willReturn(bojRankResponseList);
		given(responseService.getDataResponse(bojRankResponseList, RESPONSE_NO_CONTENT)).willReturn(
			getDataResponse(bojRankResponseList, RESPONSE_NO_CONTENT));

		//when
		ResultActions actions = mockMvc.perform(get("/boj/ranks")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));

		//then
		actions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.data", hasSize(0)))
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("조회된 데이터가 없습니다."));
	}

	private <T> DataResponse<T> getDataResponse(T data, CustomSuccessStatus status) {
		DataResponse<T> response = new DataResponse<>();
		response.setStatus(status.getStatus().toString());
		response.setMessage(status.getMessage());
		response.setData(data);

		return response;
	}

}
