package com.ssafy.backend.domain.job.service;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ssafy.backend.domain.entity.JobHistory;
import com.ssafy.backend.domain.entity.JobPosting;
import com.ssafy.backend.domain.entity.JobStatus;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.job.dto.JobApplyRegistrationRequest;
import com.ssafy.backend.domain.job.repository.JobHistoryQueryRepository;
import com.ssafy.backend.domain.job.repository.JobHistoryRepository;
import com.ssafy.backend.domain.job.repository.JobPostingRepository;
import com.ssafy.backend.domain.job.repository.JobStatusRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.global.response.exception.CustomException;
import com.ssafy.backend.global.response.exception.CustomExceptionStatus;

@ExtendWith(MockitoExtension.class)
@DisplayName("취업 지원 현황 관련 테스트")
class JobApplyServiceImplTest {

	@Mock
	private JobHistoryRepository jobHistoryRepository;
	@Mock
	private JobHistoryQueryRepository jobHistoryQueryRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private JobPostingRepository jobPostingRepository;
	@Mock
	private JobStatusRepository jobStatusRepository;

	@InjectMocks
	private JobApplyServiceImpl jobApplyServiceImpl;

	@Test
	@DisplayName("취업 지원 현황 등록")
	void createJobApplyTest() {
		//given
		JobApplyRegistrationRequest jobApplyRegistrationRequest1 = createJobApplyRequest(1L); //취업공고 1L - 예외 터짐
		JobApplyRegistrationRequest jobApplyRegistrationRequest2 = createJobApplyRequest(2L); //취업공고 2L - 예외 안터짐

		//when
		//유저 조회
		when(userRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.empty()); //유저가 없는 경우
		when(userRepository.findByIdAndIsDeletedFalse(2L)).thenReturn(Optional.of(createUser())); //유저가 있는 경우.

		//취업 공고 조회.
		when(jobPostingRepository.findByIdAndIsCloseFalse(1L)).thenReturn(Optional.empty()); // 취업 공고가 없는 경우
		when(jobPostingRepository.findByIdAndIsCloseFalse(2L)).thenReturn(
			Optional.of(createJobPosting(2L))); //취업공고가 있는 경우.

		//then
		//유저 예외
		Assertions.assertThatThrownBy(() -> jobApplyServiceImpl.createJobApply(1L, jobApplyRegistrationRequest1))
			.isInstanceOf(CustomException.class).hasFieldOrPropertyWithValue("customExceptionStatus",
				CustomExceptionStatus.NOT_FOUND_USER);

		//취업 공고 조회 예외
		Assertions.assertThatThrownBy(() -> jobApplyServiceImpl.createJobApply(2L, jobApplyRegistrationRequest1))
			.isInstanceOf(CustomException.class).hasFieldOrPropertyWithValue("customExceptionStatus",
				CustomExceptionStatus.NOT_FOUND_JOBPOSTING);

		//문제 없음
		Assertions.assertThatCode(() -> jobApplyServiceImpl.createJobApply(2L, jobApplyRegistrationRequest2))
			.doesNotThrowAnyException();
	}

	@Test
	@DisplayName("취업 지원 현황 조회")
	void getJobApplyListTest() {
		//given
		User user = createUser();

		List<Long> statusIdList = Arrays.asList(1L, 2L);

		List<JobHistory> jobHistoryListAll = new ArrayList<>();
		jobHistoryListAll.add(creatJobHistory(user, createJobPosting(1L), "서류 마감", 1L));
		jobHistoryListAll.add(creatJobHistory(user, createJobPosting(2L), "코딩테스트", 2L));
		jobHistoryListAll.add(creatJobHistory(user, createJobPosting(3L), "직무 면접", 3L));
		jobHistoryListAll.add(creatJobHistory(user, createJobPosting(4L), "최종 면접", 4L));

		List<JobHistory> jobHistoryListCondition = new ArrayList<>();
		jobHistoryListCondition.add(creatJobHistory(user, createJobPosting(1L), "서류 마감", 1L));
		jobHistoryListCondition.add(creatJobHistory(user, createJobPosting(2L), "코딩테스트", 2L));

		//취업 상태 테이블
		List<JobStatus> jobStatusList = new ArrayList<>();
		jobStatusList.add(createJobStatus(1L, "서류 탈락"));
		jobStatusList.add(createJobStatus(2L, "코테 탈락"));
		jobStatusList.add(createJobStatus(3L, "서류 통과"));
		jobStatusList.add(createJobStatus(4L, "스킵"));

		//when
		//취업 상태 테이블 조회
		when(jobStatusRepository.findAll()).thenReturn(jobStatusList);

		//유저 조회
		when(userRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.empty()); //유저가 없는 경우
		when(userRepository.findByIdAndIsDeletedFalse(2L)).thenReturn(Optional.of(user)); //유저가 있는 경우.

		when(jobHistoryQueryRepository.findByUserJoinPosting(2L, statusIdList)).thenReturn(
			jobHistoryListCondition); //조건
		when(jobHistoryQueryRepository.findByUserJoinPosting(2L, null)).thenReturn(jobHistoryListAll); //전체

		//then
		//유저 예외
		Assertions.assertThatThrownBy(() -> jobApplyServiceImpl.getJobApplies(1L, statusIdList))
			.isInstanceOf(CustomException.class).hasFieldOrPropertyWithValue("customExceptionStatus",
				CustomExceptionStatus.NOT_FOUND_USER);

		//조건 2개
		Assertions.assertThat(jobApplyServiceImpl.getJobApplies(2L, statusIdList))
			.isNotEmpty()
			.hasSize(2)
			.extracting("dDayName").contains("서류 마감", "코딩테스트");

		//조건 4개
		Assertions.assertThat(jobApplyServiceImpl.getJobApplies(2L, null))
			.isNotEmpty()
			.hasSize(4)
			.extracting("dDayName").contains("서류 마감", "코딩테스트", "직무 면접", "최종 면접");

	}

	public JobApplyRegistrationRequest createJobApplyRequest(long jobPostingId) {
		return new JobApplyRegistrationRequest(1, jobPostingId, "백엔드 개발자", "또 탈락", "2023-04-16", "코테 마감");
	}

	public User createUser() {

		return User.builder()
			.id(2L)
			.nickname("test1")
			.image("testImage")
			.bojId("testBoj")
			.refreshToken("testRefresh")
			.isDeleted(false)
			.build();
	}

	public JobPosting createJobPosting(long jobPostingId) {
		return JobPosting.builder()
			.id(jobPostingId)
			.companyName("testCompany")
			.name("testName")
			.startTime(LocalDateTime.now())
			.endTime(LocalDateTime.now())
			.isClose(false)
			.build();
	}

	public JobHistory creatJobHistory(User user, JobPosting jobPosting, String dDayName, long statusId) {

		LocalDate nowDate = LocalDate.now();

		return JobHistory.builder()
			.dDay(nowDate)
			.dDayName(dDayName)
			.memo("떨어졌다")
			.statusId(statusId)
			.jobObjective("소프트웨어 엔지니어")
			.isDeleted(false)
			.user(user)
			.jobPosting(jobPosting).build();
	}

	public JobStatus createJobStatus(long id, String name) {

		return JobStatus.builder()
			.id(id)
			.name(name)
			.build();

	}

}