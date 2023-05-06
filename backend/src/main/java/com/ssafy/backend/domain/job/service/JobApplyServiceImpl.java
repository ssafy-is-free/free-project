package com.ssafy.backend.domain.job.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.backend.domain.entity.JobHistory;
import com.ssafy.backend.domain.entity.JobPosting;
import com.ssafy.backend.domain.entity.JobStatus;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.job.dto.JobApplyDetailResponse;
import com.ssafy.backend.domain.job.dto.JobApplyRegistrationRequest;
import com.ssafy.backend.domain.job.dto.JobApplyResponse;
import com.ssafy.backend.domain.job.dto.JobApplyUpdateRequest;
import com.ssafy.backend.domain.job.repository.JobHistoryQueryRepository;
import com.ssafy.backend.domain.job.repository.JobHistoryRepository;
import com.ssafy.backend.domain.job.repository.JobPostingRepository;
import com.ssafy.backend.domain.job.repository.JobStatusRepository;
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
	private final JobStatusRepository jobStatusRepository;

	@Transactional
	@Override
	public void createJobApply(long userId, JobApplyRegistrationRequest jobApplyRegistrationRequest) {

		//유저 존재 유무 확인
		User user = userRepository.findByIdAndIsDeletedFalse(userId)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_USER));

		//해당 공고가 있는지 확인
		JobPosting jobPosting = jobPostingRepository.findByIdAndIsCloseFalse(
				jobApplyRegistrationRequest.getJobPostingId())
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_JOBPOSTING));

		//취업이력 entitiy 생성
		JobHistory jobHistory = JobHistory.create(jobApplyRegistrationRequest, user, jobPosting);

		//저장.
		jobHistoryRepository.save(jobHistory);

	}

	@Transactional(readOnly = true)
	@Override
	public List<JobApplyResponse> getJobApplies(long userId, List<Long> statusIdList) {

		//유저 존재 유무 확인
		User user = userRepository.findByIdAndIsDeletedFalse(userId)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_USER));

		//취업이력 전부 조회
		List<JobHistory> jobHistoryList = jobHistoryQueryRepository.findByUserJoinPosting(user.getId(), statusIdList);

		//취업상태 테이블 조회
		List<JobStatus> jobStatusList = jobStatusRepository.findAll();

		//DTO 변환
		return JobApplyResponse.createList(jobHistoryList, jobStatusList);
	}

	@Transactional
	@Override
	public void updateJobApply(long userId, long jobHistoryId, JobApplyUpdateRequest jobApplyUpdateRequest) {

		//유저 존재 유무 확인
		User user = userRepository.findByIdAndIsDeletedFalse(userId)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_USER));

		//해당 취업 이력 확인
		JobHistory jobHistory = jobHistoryRepository.findByIdAndIsDeletedFalse(jobHistoryId)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_JOBHISTORY));

		//취업이력 업데이트
		jobHistory.update(jobApplyUpdateRequest);
	}

	@Transactional(readOnly = true)
	@Override
	public JobApplyDetailResponse getJobApply(long userId, long jobHistoryId) {

		//유저 존재 유무 확인

		//해당 취업이력 조회.

		//취업 상태 테이블 조회.

		return null;
	}

	@Transactional
	@Override
	public void deleteJobApply(long userId, List<Long> jobHistoryId) {

		//유저 존재 유무 확인

		//해당 id 삭제처리.

	}
}
