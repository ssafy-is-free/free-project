package com.ssafy.backend.domain.job.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.backend.domain.entity.JobPosting;
import com.ssafy.backend.domain.entity.JobStatus;
import com.ssafy.backend.domain.job.dto.JobPostingResponse;
import com.ssafy.backend.domain.job.dto.JobStatusResponse;
import com.ssafy.backend.domain.job.repository.JobPostingQueryRepository;
import com.ssafy.backend.domain.job.repository.JobStatusRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class JobUtilServiceImpl implements JobUtilService {

	private final JobPostingQueryRepository jobPostingQueryRepository;
	private final JobStatusRepository jobStatusRepository;

	@Override
	public List<JobPostingResponse> getJobPosting(String keyword) {

		//키워드로 취업 공고 조회
		List<JobPosting> jobPostingList = jobPostingQueryRepository.findByNameLikeAndIsCloseFalse(keyword);

		return JobPostingResponse.createList(jobPostingList);
	}

	@Override
	public List<JobStatusResponse> getJobStatus() {

		//공고 상태 조회
		List<JobStatus> jobStatusList = jobStatusRepository.findAll();

		return JobStatusResponse.createList(jobStatusList);
	}
}
