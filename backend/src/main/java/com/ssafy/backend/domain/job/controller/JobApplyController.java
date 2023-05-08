package com.ssafy.backend.domain.job.controller;

import static com.ssafy.backend.global.response.CustomSuccessStatus.*;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.domain.job.dto.JobApplyDeleteRequest;
import com.ssafy.backend.domain.job.dto.JobApplyDetailResponse;
import com.ssafy.backend.domain.job.dto.JobApplyRegistrationRequest;
import com.ssafy.backend.domain.job.dto.JobApplyResponse;
import com.ssafy.backend.domain.job.dto.JobApplyUpdateRequest;
import com.ssafy.backend.domain.job.service.JobApplyService;
import com.ssafy.backend.global.auth.dto.UserPrincipal;
import com.ssafy.backend.global.response.CommonResponse;
import com.ssafy.backend.global.response.DataResponse;
import com.ssafy.backend.global.response.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 취업 지원 관련
 * 현황등록, 현황조회, 현황 수정, 상세정보 조회,선택 삭제
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/job")
public class JobApplyController {

	private final JobApplyService jobApplyService;
	private final ResponseService responseService;

	//지원 현황 등록
	@PostMapping
	public CommonResponse createJob(
		@RequestBody JobApplyRegistrationRequest jobApplyRegistrationRequest,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {

		long userId = userPrincipal.getId();

		jobApplyService.createJobApply(userId, jobApplyRegistrationRequest);

		return responseService.getSuccessResponse();
	}

	//todo 페이징 처리 필요
	//지원 현황 조회
	@GetMapping
	public DataResponse<List<JobApplyResponse>> getAllJob(
		@RequestParam(value = "statusId", required = false) List<Long> statusIdList,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {

		long userId = userPrincipal.getId();

		List<JobApplyResponse> jobApplyResponseList = jobApplyService.getJobApplies(userId, statusIdList);

		return jobApplyResponseList.isEmpty() ?
			responseService.getDataResponse(jobApplyResponseList, RESPONSE_NO_CONTENT) :
			responseService.getDataResponse(jobApplyResponseList, RESPONSE_SUCCESS);
	}

	//지원 현황 수정
	@PatchMapping("/history/{jobHistoryId}")
	public CommonResponse updateJob(
		@PathVariable("jobHistoryId") long jobHistoryId,
		@RequestBody JobApplyUpdateRequest jobApplyUpdateRequest,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {

		long userId = userPrincipal.getId();

		jobApplyService.updateJobApply(userId, jobHistoryId, jobApplyUpdateRequest);

		return responseService.getSuccessResponse();
	}

	//지원 상세정보 조회
	@GetMapping("/history/{jobHistoryId}")
	public DataResponse<JobApplyDetailResponse> getJobByJobId(
		@PathVariable("jobHistoryId") long jobHistoryId,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {

		// long userId = userPrincipal.getId();
		long userId = 2L;

		return responseService.getDataResponse(jobApplyService.getJobApply(userId, jobHistoryId), RESPONSE_SUCCESS);

	}

	//지원 현황 선택 삭제
	@DeleteMapping("/history")
	public CommonResponse deleteJob(
		@RequestBody JobApplyDeleteRequest jobApplyDeleteRequest,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {

		long userId = userPrincipal.getId();

		jobApplyService.deleteJobApply(userId, jobApplyDeleteRequest.getHistoryId());

		return responseService.getSuccessResponse();
	}
}
