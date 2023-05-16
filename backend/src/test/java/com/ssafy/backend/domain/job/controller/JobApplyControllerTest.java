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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.backend.PrincipalDetailsArgumentResolver;
import com.ssafy.backend.domain.job.dto.JobApplyDeleteRequest;
import com.ssafy.backend.domain.job.dto.JobApplyDetailResponse;
import com.ssafy.backend.domain.job.dto.JobApplyRegistrationRequest;
import com.ssafy.backend.domain.job.dto.JobApplyResponse;
import com.ssafy.backend.domain.job.dto.JobApplyUpdateRequest;
import com.ssafy.backend.domain.job.service.JobApplyService;
import com.ssafy.backend.domain.util.service.NotificationManager;
import com.ssafy.backend.global.auth.filter.TokenAuthenticationFilter;
import com.ssafy.backend.global.config.sercurity.SecurityConfig;
import com.ssafy.backend.global.exception.ControllerAdvisor;
import com.ssafy.backend.global.response.CommonResponse;
import com.ssafy.backend.global.response.CustomSuccessStatus;
import com.ssafy.backend.global.response.DataResponse;
import com.ssafy.backend.global.response.ResponseService;
import com.ssafy.backend.global.response.ResponseStatus;

@WebMvcTest(controllers = JobApplyController.class
	, excludeFilters = {@ComponentScan.Filter(
	type = FilterType.ASSIGNABLE_TYPE,
	classes = {SecurityConfig.class, TokenAuthenticationFilter.class, NotificationManager.class,
		ControllerAdvisor.class}
)
})
@WithMockUser
public class JobApplyControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private JobApplyService jobApplyService;
	@MockBean
	private ResponseService responseService;
	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void before() {
		mockMvc = MockMvcBuilders
			.standaloneSetup(new JobApplyController(jobApplyService, responseService))
			.setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver(),
				new PageableHandlerMethodArgumentResolver())
			.build();
	}

	@Test
	@DisplayName("취업 지원 만들기 테스트")
	public void createJobTest() throws Exception {
		//given
		given(responseService.getSuccessResponse()).willReturn(getSuccessResponse());

		JobApplyRegistrationRequest request = new JobApplyRegistrationRequest();
		request.setDDay("I Can Do This All Day");
		//when
		ResultActions actions = mockMvc.perform(post("/job")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));
		//then
		actions
			.andDo(print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("요청에 성공했습니다."));

	}

	@Test
	@DisplayName("취업 지원 현황 조회 테스트")
	public void getAllJobTest() throws Exception {
		//given
		List<JobApplyResponse> jobApplyResponseList = new ArrayList<>();
		jobApplyResponseList.add(JobApplyResponse.builder().build());

		given(jobApplyService.getJobApplies(anyLong(), anyList(), anyString(), anyLong(),
			any(Pageable.class))).willReturn(jobApplyResponseList);
		given(responseService.getDataResponse(jobApplyResponseList, RESPONSE_SUCCESS)).willReturn(
			getDataResponse(jobApplyResponseList, RESPONSE_SUCCESS));

		ResultActions actions = mockMvc.perform(
			get("/job?statusId=1,2,3&nextDate=2023-05-16&jobHistoryId=10&page=0&size=20")
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
	@DisplayName("취업 지원 현황 조회 Null 테스트")
	public void getAllJobNullTest() throws Exception {
		//given
		List<JobApplyResponse> jobApplyResponseList = new ArrayList<>();

		given(jobApplyService.getJobApplies(anyLong(), anyList(), anyString(), anyLong(),
			any(Pageable.class))).willReturn(jobApplyResponseList);
		given(responseService.getDataResponse(jobApplyResponseList, RESPONSE_NO_CONTENT)).willReturn(
			getDataResponse(jobApplyResponseList, RESPONSE_NO_CONTENT));

		ResultActions actions = mockMvc.perform(
			get("/job?statusId=1,2,3&nextDate=2023-05-16&jobHistoryId=10&page=0&size=20")
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
	@DisplayName("지원 현황 수정 테스트")
	public void updateJobTest() throws Exception {
		//given
		given(responseService.getSuccessResponse()).willReturn(getSuccessResponse());

		JobApplyUpdateRequest request = new JobApplyUpdateRequest();
		request.setDDayName("I Can Do This All Day!!");
		//when
		ResultActions actions = mockMvc.perform(patch("/job/history/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));
		//then
		actions
			.andDo(print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.status").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("요청에 성공했습니다."));

	}

	@Test
	@DisplayName("지원 상세정보 조회 테스트")
	public void getJobByJobIdTest() throws Exception {
		//given
		JobApplyDetailResponse response = JobApplyDetailResponse.builder().build();

		given(jobApplyService.getJobApply(anyLong(), anyLong())).willReturn(response);
		given(responseService.getDataResponse(response, RESPONSE_SUCCESS)).willReturn(
			getDataResponse(response, RESPONSE_SUCCESS));

		ResultActions actions = mockMvc.perform(
			get("/job/history/1")
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
	@DisplayName("지원 현황 삭제 테스트")
	public void deleteJobTest() throws Exception {
		//given
		given(responseService.getSuccessResponse()).willReturn(getSuccessResponse());

		JobApplyDeleteRequest request = new JobApplyDeleteRequest();
		request.setHistoryId(new ArrayList<>());
		//when
		ResultActions actions = mockMvc.perform(delete("/job/history")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));
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
