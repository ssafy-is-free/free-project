package com.ssafy.backend.domain.job.service;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ssafy.backend.domain.entity.JobPosting;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.job.dto.JobApplyRegistrationRequest;
import com.ssafy.backend.domain.job.repository.JobHistoryQueryRepository;
import com.ssafy.backend.domain.job.repository.JobHistoryRepository;
import com.ssafy.backend.domain.job.repository.JobPostingRepository;
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

	@InjectMocks
	private JobApplyServiceImpl jobApplyServiceImpl;

	@Test
	@DisplayName("취업 지원 현황 등록")
	void jobApplyCreateTest() {
		//given
		JobApplyRegistrationRequest jobApplyRegistrationRequest1 = createJobApplyRequest(1L); //취업공고 1L - 예외 터짐
		JobApplyRegistrationRequest jobApplyRegistrationRequest2 = createJobApplyRequest(2L); //취업공고 2L - 예외 안터짐

		//when
		//유저 조회
		when(userRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.empty()); //유저가 없는 경우
		when(userRepository.findByIdAndIsDeletedFalse(2L)).thenReturn(Optional.of(createUser())); //유저가 있는 경우.

		//취업 공고 조회.
		when(jobPostingRepository.findByIdAndIsClosedFalse(1L)).thenReturn(Optional.empty()); // 취업 공고가 없는 경우
		when(jobPostingRepository.findByIdAndIsClosedFalse(2L)).thenReturn(
			Optional.of(createJobPosting())); //취업공고가 있는 경우.

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

	public JobPosting createJobPosting() {
		return JobPosting.builder()
			.id(2L)
			.companyName("testCompany")
			.name("testName")
			.startTime(LocalDateTime.now())
			.endTime(LocalDateTime.now())
			.isClose(false)
			.build();
	}

}