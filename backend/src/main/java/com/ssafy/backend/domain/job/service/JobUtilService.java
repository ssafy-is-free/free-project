package com.ssafy.backend.domain.job.service;

import java.util.List;

import com.ssafy.backend.domain.job.dto.JobPostingResponse;
import com.ssafy.backend.domain.job.dto.JobStatusResponse;

public interface JobUtilService {

	//취업 공고 조회
	List<JobPostingResponse> getJobPosting(String keyword);

	//취업 상태 조회
	List<JobStatusResponse> getJobStatus();
}
