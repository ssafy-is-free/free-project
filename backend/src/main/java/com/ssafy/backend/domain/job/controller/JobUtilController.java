package com.ssafy.backend.domain.job.controller;

import static com.ssafy.backend.global.response.CustomSuccessStatus.*;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.domain.job.dto.JobPostingResponse;
import com.ssafy.backend.domain.job.dto.JobStatusResponse;
import com.ssafy.backend.domain.job.service.JobUtilService;
import com.ssafy.backend.global.response.DataResponse;
import com.ssafy.backend.global.response.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//취업상태, 취업공고 조회
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/job")
public class JobUtilController {

	private final JobUtilService jobUtilService;
	private final ResponseService responseService;

	//취업 공고 조회
	@GetMapping("/posting")
	public DataResponse<List<JobPostingResponse>> getJobPosting(
		@RequestParam(value = "keyword", required = false) String keyword) {

		List<JobPostingResponse> jobPostingResponseList = jobUtilService.getJobPosting(keyword);

		return jobPostingResponseList.isEmpty() ?
			responseService.getDataResponse(jobPostingResponseList, RESPONSE_NO_CONTENT) :
			responseService.getDataResponse(jobPostingResponseList, RESPONSE_SUCCESS);

	}

	@GetMapping("/status")
	public DataResponse<List<JobStatusResponse>> getJobStatus() {

		List<JobStatusResponse> jobStatusResponseList = jobUtilService.getJobStatus();

		return jobStatusResponseList.isEmpty() ?
			responseService.getDataResponse(jobStatusResponseList, RESPONSE_NO_CONTENT) :
			responseService.getDataResponse(jobStatusResponseList, RESPONSE_SUCCESS);

	}

}