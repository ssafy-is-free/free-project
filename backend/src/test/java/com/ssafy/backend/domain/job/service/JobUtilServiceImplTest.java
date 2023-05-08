package com.ssafy.backend.domain.job.service;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ssafy.backend.domain.entity.JobPosting;
import com.ssafy.backend.domain.entity.JobStatus;
import com.ssafy.backend.domain.job.dto.JobPostingResponse;
import com.ssafy.backend.domain.job.dto.JobStatusResponse;
import com.ssafy.backend.domain.job.repository.JobPostingQueryRepository;
import com.ssafy.backend.domain.job.repository.JobStatusRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("취업관리에 필요한 유틸 기능 테스트")
class JobUtilServiceImplTest {

	@Mock
	private JobPostingQueryRepository jobPostingQueryRepository;
	@Mock
	private JobStatusRepository jobStatusRepository;
	@InjectMocks
	private JobUtilServiceImpl jobUtilService;

	// @BeforeEach
	// void setup() {
	// 	jobUtilService = new JobUtilServiceImpl(jobPostingQueryRepository, jobStatusRepository);
	// }

	@Test
	@DisplayName("취업 공고 조회")
	public void getJobPosting() {

		LocalDate testTime = LocalDate.now();

		List<JobPosting> jobPostingListNull = new ArrayList<>();
		jobPostingListNull.add(
			new JobPosting(1, "삼성전자", "2023년 신입공채", testTime, testTime, false));
		jobPostingListNull.add(
			new JobPosting(2, "삼성전기", "2021년 신입공채", testTime, testTime, false));
		jobPostingListNull.add(
			new JobPosting(3, "삼성SDS", "2023년 신입공채", testTime, testTime, false));
		jobPostingListNull.add(
			new JobPosting(4, "네이버", "2023년 신입공채", testTime, testTime, false));

		List<JobPosting> jobPostingListKeyword = new ArrayList<>();
		jobPostingListKeyword.add(
			new JobPosting(1, "삼성전자", "삼전 2023년 신입공채", testTime, testTime, false));
		jobPostingListKeyword.add(
			new JobPosting(2, "삼성전기", "삼전 2021년 신입공채", testTime, testTime, false));

		//해당 키워드로 조회했을 때
		when(jobPostingQueryRepository.findByNameLikeAndIsCloseFalse(null)).thenReturn(jobPostingListNull);
		when(jobPostingQueryRepository.findByNameLikeAndIsCloseFalse("삼전")).thenReturn(jobPostingListKeyword);

		List<JobPostingResponse> jobPostingResponseListNull = jobUtilService.getJobPosting(null);
		List<JobPostingResponse> jobPostingResponseListKeyword = jobUtilService.getJobPosting("삼전");

		Assertions.assertEquals(jobPostingResponseListNull.size(), 4);
		Assertions.assertEquals(jobPostingResponseListKeyword.size(), 2);

	}

	@Test
	@DisplayName("취업 상태 조회")
	public void getJobStatusResponse() {
		List<JobStatus> jobStatusList = new ArrayList<>();

		jobStatusList.add(new JobStatus(1, "서류 대기중"));
		jobStatusList.add(new JobStatus(2, "면접 대기중"));
		jobStatusList.add(new JobStatus(3, "서류 탈락"));
		jobStatusList.add(new JobStatus(4, "면접 탈락"));

		when(jobStatusRepository.findAll()).thenReturn(jobStatusList);

		List<JobStatusResponse> jobStatus = jobUtilService.getJobStatus();

		Assertions.assertEquals(jobStatus.size(), 4);

	}
}