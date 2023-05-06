package com.ssafy.backend.domain.job.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.backend.domain.entity.JobHistory;
import com.ssafy.backend.domain.entity.JobPosting;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.job.dto.JobApplyDetailResponse;
import com.ssafy.backend.domain.job.dto.JobApplyRegistrationRequest;
import com.ssafy.backend.domain.job.dto.JobApplyResponse;
import com.ssafy.backend.domain.job.dto.JobApplyUpdateRequest;
import com.ssafy.backend.domain.job.repository.JobHistoryQueryRepository;
import com.ssafy.backend.domain.job.repository.JobHistoryRepository;
import com.ssafy.backend.domain.job.repository.JobPostingRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.global.response.exception.CustomException;
import com.ssafy.backend.global.response.exception.CustomExceptionStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JobApplyServiceImpl implements JobApplyService {

	private final JobHistoryRepository jobHistoryRepository;
	private final JobHistoryQueryRepository jobHistoryQueryRepository;
	private final UserRepository userRepository;
	private final JobPostingRepository jobPostingRepository;

	@Override
	public void createJobApply(long userId, JobApplyRegistrationRequest jobApplyRegistrationRequest) {

		//유저 존재 유무 확인
		User user = userRepository.findByIdAndIsDeletedFalse(userId)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_USER));

		//해당 공고가 있는지 확인
		JobPosting jobPosting = jobPostingRepository.findByIdAndIsClosedFalse(
				jobApplyRegistrationRequest.getJobPostingId())
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_JOBPOSTING));

		//취업이력 entitiy 생성
		JobHistory jobHistory = JobHistory.create(jobApplyRegistrationRequest, user, jobPosting);

		//저장.
		jobHistoryRepository.save(jobHistory);

	}

	@Override
	public List<JobApplyResponse> getJobApplyList(long userId) {
		return null;
	}

	@Override
	public void updateJobApply(long userId, JobApplyUpdateRequest jobApplyUpdateRequest) {

	}

	@Override
	public JobApplyDetailResponse getJobApply(long userId, long jobHistoryId) {
		return null;
	}

	@Override
	public void deleteJobApply(long userId, List<Long> jobHistoryId) {

	}
}
