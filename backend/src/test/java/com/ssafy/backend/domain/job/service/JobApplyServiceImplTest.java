package com.ssafy.backend.domain.job.service;

import static com.ssafy.backend.global.response.exception.CustomExceptionStatus.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
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
import org.springframework.data.domain.PageRequest;

import com.ssafy.backend.domain.entity.JobHistory;
import com.ssafy.backend.domain.entity.JobPosting;
import com.ssafy.backend.domain.entity.JobStatus;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.job.dto.JobApplyRegistrationRequest;
import com.ssafy.backend.domain.job.dto.JobApplyUpdateRequest;
import com.ssafy.backend.domain.job.repository.JobHistoryQueryRepository;
import com.ssafy.backend.domain.job.repository.JobHistoryRepository;
import com.ssafy.backend.domain.job.repository.JobPostingRepository;
import com.ssafy.backend.domain.job.repository.JobStatusRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.global.response.exception.CustomException;

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
			Optional.ofNullable(createJobPosting(2L))); //취업공고가 있는 경우.

		//then
		//유저 예외
		Assertions.assertThatThrownBy(() -> jobApplyServiceImpl.createJobApply(1L, jobApplyRegistrationRequest1))
			.isInstanceOf(CustomException.class).hasFieldOrPropertyWithValue("customExceptionStatus",
				NOT_FOUND_USER);

		//취업 공고 조회 예외
		Assertions.assertThatThrownBy(() -> jobApplyServiceImpl.createJobApply(2L, jobApplyRegistrationRequest1))
			.isInstanceOf(CustomException.class).hasFieldOrPropertyWithValue("customExceptionStatus",
				NOT_FOUND_JOBPOSTING);

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

		when(jobHistoryQueryRepository.findByUserJoinPosting(2L, statusIdList, "2023-05-11", 1L,
			PageRequest.of(0, 5))).thenReturn(
			jobHistoryListCondition); //조건
		when(jobHistoryQueryRepository.findByUserJoinPosting(2L, null, "2023-05-11", 1L,
			PageRequest.of(0, 5))).thenReturn(jobHistoryListAll); //전체

		//then
		//유저 예외
		Assertions.assertThatThrownBy(() -> jobApplyServiceImpl.getJobApplies(1L, statusIdList, "2023-05-11", 1L,
				PageRequest.of(0, 5)))
			.isInstanceOf(CustomException.class).hasFieldOrPropertyWithValue("customExceptionStatus",
				NOT_FOUND_USER);

		//조건 2개
		Assertions.assertThat(jobApplyServiceImpl.getJobApplies(2L, statusIdList, "2023-05-11", 1L,
				PageRequest.of(0, 5)))
			.isNotEmpty()
			.hasSize(2)
			.extracting("dDayName").contains("서류 마감", "코딩테스트");

		//조건 4개
		Assertions.assertThat(jobApplyServiceImpl.getJobApplies(2L, null, "2023-05-11", 1L,
				PageRequest.of(0, 5)))
			.isNotEmpty()
			.hasSize(4)
			.extracting("dDayName").contains("서류 마감", "코딩테스트", "직무 면접", "최종 면접");

	}

	@Test
	@DisplayName("취업 지원 현황 수정")
	void updateJobApply() {
		//given
		User user = createUser();
		JobApplyUpdateRequest jobApplyUpdateRequest = updateJobApplyRequest();

		List<JobStatus> jobStatusList = new ArrayList<>();
		jobStatusList.add(createJobStatus(1L, "서류 탈락"));
		jobStatusList.add(createJobStatus(2L, "코테 탈락"));
		jobStatusList.add(createJobStatus(3L, "서류 통과"));
		jobStatusList.add(createJobStatus(4L, "스킵"));

		//when
		//유저 조회
		when(userRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.empty()); //유저가 없는 경우
		when(userRepository.findByIdAndIsDeletedFalse(2L)).thenReturn(Optional.of(user)); //유저가 있는 경우.

		when(jobHistoryRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(
			Optional.ofNullable(creatJobHistory(user, createJobPosting(1L), "dDayName", 1L))); //공고가 있는 경우
		when(jobHistoryRepository.findByIdAndIsDeletedFalse(2L)).thenReturn(Optional.empty()); //공고가 없는 경우.

		//then
		//유저 예외
		Assertions.assertThatThrownBy(() -> jobApplyServiceImpl.updateJobApply(1L, 1L, jobApplyUpdateRequest))
			.isInstanceOf(CustomException.class)
			.hasFieldOrPropertyWithValue("customExceptionStatus", NOT_FOUND_USER);

		//취업 공고 예외.
		Assertions.assertThatThrownBy(() -> jobApplyServiceImpl.updateJobApply(2L, 2L, jobApplyUpdateRequest))
			.isInstanceOf(CustomException.class)
			.hasFieldOrPropertyWithValue("customExceptionStatus", NOT_FOUND_JOBHISTORY);

		//문제 없음
		Assertions.assertThatCode(() -> jobApplyServiceImpl.updateJobApply(2L, 1L, jobApplyUpdateRequest))
			.doesNotThrowAnyException();
	}

	@Test
	@DisplayName("취업 지원 상세정보 조회")
	void getJobApplyDetailTest() {
		//given
		User user = createUser();
		JobHistory jobHistory = creatJobHistory(user, createJobPosting(1L), "dDayName", 1L);

		//취업 상태 테이블
		List<JobStatus> jobStatusList = new ArrayList<>();
		jobStatusList.add(createJobStatus(1L, "서류 탈락"));
		jobStatusList.add(createJobStatus(2L, "코테 탈락"));
		jobStatusList.add(createJobStatus(3L, "서류 통과"));
		jobStatusList.add(createJobStatus(4L, "스킵"));

		//when
		//유저 조회
		when(userRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.empty()); //유저가 없는 경우
		when(userRepository.findByIdAndIsDeletedFalse(2L)).thenReturn(Optional.of(user)); //유저가 있는 경우.

		//취업 이력 조회
		when(jobHistoryQueryRepository.findByIdJoinPosting(2L, 1L)).thenReturn(Optional.of(jobHistory)); //값이 있는 경우
		when(jobHistoryQueryRepository.findByIdJoinPosting(2L, 2L)).thenReturn(Optional.empty()); //값이 없는 경우

		//취업 상태 테이블 조회
		when(jobStatusRepository.findAll()).thenReturn(jobStatusList);

		//지원자 수
		when(jobHistoryQueryRepository.countUserTotalJobHistory(1L)).thenReturn(10L);

		//then
		//없는 유저일때,
		Assertions.assertThatThrownBy(() -> jobApplyServiceImpl.getJobApply(1L, 1L))
			.isInstanceOf(CustomException.class)
			.hasFieldOrPropertyWithValue("customExceptionStatus", NOT_FOUND_USER);

		//없는 이력일때,
		Assertions.assertThatThrownBy(() -> jobApplyServiceImpl.getJobApply(2L, 2L))
			.isInstanceOf(CustomException.class)
			.hasFieldOrPropertyWithValue("customExceptionStatus", NOT_FOUND_JOBHISTORY);

		//옳은 요청1
		Assertions.assertThat(jobApplyServiceImpl.getJobApply(2L, 1L))
			.isNotNull()
			.extracting("objective").isEqualTo("소프트웨어 엔지니어");

		Assertions.assertThat(jobApplyServiceImpl.getJobApply(2L, 1L))
			.isNotNull()
			.extracting("applicantCount").isEqualTo(10L);
	}

	//todo 테스트 수정 필요 - 취업 이력이 없는 경우 예외 터트리는 테스트 필요한데 로직쪽에서 벌크 연산을 써서 할 수 없음.
	@Test
	@DisplayName("취업현황 선택 삭제")
	void deleteJobApplyTest() {
		//given
		User user = createUser();
		JobHistory jobHistory = creatJobHistory(user, createJobPosting(1L), "dDayName", 1L);

		List<Long> jobHistoryIds1 = Arrays.asList(1L);
		List<Long> jobHistoryIds2 = Arrays.asList(2L);

		//when
		//유저 조회
		when(userRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.empty()); //유저가 없는 경우
		when(userRepository.findByIdAndIsDeletedFalse(2L)).thenReturn(Optional.of(user)); //유저가 있는 경우.

		//취업 이력 조회
		when(jobHistoryQueryRepository.deleteBulk(2L, jobHistoryIds1)).thenReturn(1L); //값이 있는 경우

		//then
		//없는 유저일때,
		Assertions.assertThatThrownBy(() -> jobApplyServiceImpl.deleteJobApply(1L, jobHistoryIds1))
			.isInstanceOf(CustomException.class)
			.hasFieldOrPropertyWithValue("customExceptionStatus", NOT_FOUND_USER);

		//옳은 요청
		Assertions.assertThatCode(() -> jobApplyServiceImpl.deleteJobApply(2L, jobHistoryIds1))
			.doesNotThrowAnyException();
	}

	public JobApplyRegistrationRequest createJobApplyRequest(long jobPostingId) {
		return new JobApplyRegistrationRequest(1, jobPostingId, "백엔드 개발자", "또 탈락", "2023-04-16", "코테 마감");
	}

	public JobApplyUpdateRequest updateJobApplyRequest() {
		return new JobApplyUpdateRequest(1L, "update test", "update dDayName", "2023-05-30", "데이터 엔지니어");
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
			.startTime(LocalDate.now())
			.endTime(LocalDate.now())
			.isClose(false)
			.build();
	}

	public JobHistory creatJobHistory(User user, JobPosting jobPosting, String dDayName, long statusId) {

		LocalDate nowDate = LocalDate.now();

		return JobHistory.builder()
			.id(1L)
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