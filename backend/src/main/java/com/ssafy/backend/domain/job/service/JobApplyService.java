package com.ssafy.backend.domain.job.service;

import java.util.List;

import com.ssafy.backend.domain.job.dto.JobApplyDetailResponse;
import com.ssafy.backend.domain.job.dto.JobApplyRegistrationRequest;
import com.ssafy.backend.domain.job.dto.JobApplyResponse;
import com.ssafy.backend.domain.job.dto.JobApplyUpdateRequest;

public interface JobApplyService {

	//지원 현황 등록
	void createJobApply(long userId, JobApplyRegistrationRequest jobApplyRegistrationRequest);

	//지원 현황 조회
	List<JobApplyResponse> getJobApplies(long userId, List<Long> statusIdList);

	//지원 현황 수정
	void updateJobApply(long userId, JobApplyUpdateRequest jobApplyUpdateRequest);

	//지원 상세정보 조회
	JobApplyDetailResponse getJobApply(long userId, long jobHistoryId);

	//지원 현황 선택 삭제
	void deleteJobApply(long userId, List<Long> jobHistoryId);
}
