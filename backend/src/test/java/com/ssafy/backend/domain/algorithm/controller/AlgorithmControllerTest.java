/*
package com.ssafy.backend.domain.algorithm.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ssafy.backend.domain.algorithm.service.AlgorithmService;
import com.ssafy.backend.global.response.ResponseService;

@RunWith(SpringRunner.class)
@WebMvcTest(AlgorithmController.class)
public class AlgorithmControllerTest {
	@Autowired
	MockMvc mockMvc;
	@MockBean
	private ResponseService responseService;
	@MockBean
	private AlgorithmService algorithmService;

	@Test
	@WithMockUser(username = "testUser", roles = {"bearer "})
	@DisplayName("알고리즘 컨트롤러 테스트")
	public void bojMyRankTest() throws Exception {
		*/
/*//*
/given
		given(algorithmService.getBojByUserId(any(), any(), any())).willReturn(BojRankResponse.builder()
			.userId(1L)
			.rank(1)
			.score(100)
			.nickname("Nickname")
			.avatarUrl("1")
			.tierUrl("14")
			.rankUpDown(2L)
			.build());

		//when //then
		mockMvc.perform(MockMvcRequestBuilders.get("/my-rank"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data.userId").value(1));*//*


		mockMvc.perform(MockMvcRequestBuilders.get("/my-rank")
				.param("languageId", "1")
				.param("jobPostingId", "2"))
			.andExpect(status().isOk());

	}

}
*/
