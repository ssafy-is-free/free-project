package com.ssafy.backend.domain.job.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.domain.job.dto.JobApplyDetailResponse;
import com.ssafy.backend.domain.job.dto.JobApplyRegistrationRequest;
import com.ssafy.backend.domain.job.dto.JobApplyResponse;
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
		@AuthenticationPrincipal UserPrincipal userPrincipal
	) {

		// long userId = userPrincipal.getId();
		long userId = 2L;

		jobApplyService.createJobApply(userId, jobApplyRegistrationRequest);

		return responseService.getSuccessResponse();
	}

	//지원 현황 조회
	@GetMapping
	public DataResponse<List<JobApplyResponse>> getAllJob() {

		return null;
	}

	//지원 현황 수정
	@PatchMapping("/history/{jobHistoryId}")
	public CommonResponse updateJob() {

		return responseService.getSuccessResponse();
	}

	//지원 상세정보 조회
	@GetMapping("/history/{jobHistoryId}")
	public DataResponse<JobApplyDetailResponse> getJobByJobId() {
		return null;
	}

	//지원 현황 선택 삭제
	@DeleteMapping("/history")
	public CommonResponse deleteJob() {

		return responseService.getSuccessResponse();
	}
}
