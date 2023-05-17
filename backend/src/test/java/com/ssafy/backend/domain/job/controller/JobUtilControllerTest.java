package com.ssafy.backend.domain.job.controller;

import static com.ssafy.backend.global.response.CustomSuccessStatus.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.backend.PrincipalDetailsArgumentResolver;
import com.ssafy.backend.domain.job.dto.JobPostingResponse;
import com.ssafy.backend.domain.job.dto.JobStatusResponse;
import com.ssafy.backend.domain.job.service.JobUtilService;
import com.ssafy.backend.domain.util.service.NotificationManager;
import com.ssafy.backend.global.auth.filter.TokenAuthenticationFilter;
import com.ssafy.backend.global.config.sercurity.SecurityConfig;
import com.ssafy.backend.global.exception.ControllerAdvisor;
import com.ssafy.backend.global.response.CustomSuccessStatus;
import com.ssafy.backend.global.response.DataResponse;
import com.ssafy.backend.global.response.ResponseService;

@WebMvcTest(controllers = JobUtilController.class
	, excludeFilters = {@ComponentScan.Filter(
	type = FilterType.ASSIGNABLE_TYPE,
	classes = {SecurityConfig.class, TokenAuthenticationFilter.class, NotificationManager.class,
		ControllerAdvisor.class}
)
})
@WithMockUser
public class JobUtilControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private JobUtilService jobUtilService;
	@MockBean
	private ResponseService responseService;
	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void before() {
		mockMvc = MockMvcBuilders
			.standaloneSetup(new JobUtilController(jobUtilService, responseService))
			.setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver(),
				new PageableHandlerMethodArgumentResolver())
			.build();
	}

	@Test
	@DisplayName("취업 지원 만들기 테스트")
	public void createJobTest() throws Exception {
		//given
		List<JobPostingResponse> jobPostingResponseList = new ArrayList<>();
		jobPostingResponseList.add(JobPostingResponse.builder().build());

		given(jobUtilService.getJobPosting(anyString())).willReturn(jobPostingResponseList);
		given(responseService.getDataResponse(jobPostingResponseList, RESPONSE_SUCCESS)).willReturn(
			getDataResponse(jobPostingResponseList, RESPONSE_SUCCESS));

		//when
		ResultActions actions = mockMvc.perform(get("/job/posting?keyword=공고")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));
		//then
		actions
			.andDo(print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value(RESPONSE_SUCCESS.getMessage()));

	}

	@Test
	@DisplayName("취업 지원 만들기 테스트")
	public void createJobNullTest() throws Exception {
		//given
		List<JobPostingResponse> jobPostingResponseList = new ArrayList<>();

		given(jobUtilService.getJobPosting(anyString())).willReturn(jobPostingResponseList);
		given(responseService.getDataResponse(jobPostingResponseList, RESPONSE_NO_CONTENT)).willReturn(
			getDataResponse(jobPostingResponseList, RESPONSE_NO_CONTENT));

		//when
		ResultActions actions = mockMvc.perform(get("/job/posting?keyword=공고")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));
		//then
		actions
			.andDo(print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value(RESPONSE_NO_CONTENT.getMessage()));

	}

	@Test
	@DisplayName("취업 상태 조회 테스트")
	public void getJobStatusTest() throws Exception {
		//given
		List<JobStatusResponse> responses = new ArrayList<>();
		responses.add(JobStatusResponse.builder().build());

		given(jobUtilService.getJobStatus()).willReturn(responses);
		given(responseService.getDataResponse(responses, RESPONSE_SUCCESS)).willReturn(
			getDataResponse(responses, RESPONSE_SUCCESS));

		//when
		ResultActions actions = mockMvc.perform(get("/job/status")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));
		//then
		actions
			.andDo(print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value(RESPONSE_SUCCESS.getMessage()));

	}

	@Test
	@DisplayName("취업 상태 조회 Null 테스트")
	public void getJobStatusNullTest() throws Exception {
		//given
		List<JobStatusResponse> responses = new ArrayList<>();

		given(jobUtilService.getJobStatus()).willReturn(responses);
		given(responseService.getDataResponse(responses, RESPONSE_NO_CONTENT)).willReturn(
			getDataResponse(responses, RESPONSE_NO_CONTENT));

		//when
		ResultActions actions = mockMvc.perform(get("/job/status")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8"));
		//then
		actions
			.andDo(print())
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
