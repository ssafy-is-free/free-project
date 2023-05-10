/*
package com.ssafy.backend.domain.algorithm.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.ssafy.backend.domain.algorithm.service.AlgorithmService;
import com.ssafy.backend.domain.user.dto.NicknameListResponse;
import com.ssafy.backend.global.auth.util.TokenProvider;
import com.ssafy.backend.global.response.ResponseService;

@WebMvcTest(AlgorithmController.class)
public class AlgorithmControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private TokenProvider tokenProvider;
	@Autowired
	private AuthenticationManagerBuilder authenticationManagerBuilder;
	@MockBean
	private AlgorithmService algorithmService;
	@MockBean
	private ResponseService responseService;

	@Test
	@DisplayName("알고리즘 컨트롤러 테스트")
	public void bojMyRankTest() throws Exception {

		*/
/*//*
/given
		BojRankResponse mockResponse = BojRankResponse.builder()
			.userId(1L)
			.rank(1)
			.score(100)
			.nickname("Nickname")
			.avatarUrl("1")
			.tierUrl("14")
			.rankUpDown(2L)
			.build();

		String mockToken = "mockToken";

		when(algorithmService.getBojByUserId(anyLong(), any(), any())).thenReturn(mockResponse);
		when(responseService.getDataResponse(any(), any())).thenReturn(
			new DataResponse<>()); // 적절한 DataResponse를 반환하도록 설정하세요

		//when //then
		mockMvc.perform(get("/my-rank"))
			.andExpect(status().isOk());*//*


	}

	@Test
	@DisplayName("알고리즘 컨트롤러 테스트")
	public void getBojIdListTest() throws Exception {
		//given
		List<NicknameListResponse> nicknameListResponseList = new ArrayList<>();
		nicknameListResponseList.add(new NicknameListResponse(1L, "soda"));
		//when
		when(algorithmService.getBojListByBojId(anyString())).thenReturn(nicknameListResponseList);
		//then
		mockMvc.perform(get("/search?nickname=soda"))
			.andExpect(status().isOk());
	}

}
*/
