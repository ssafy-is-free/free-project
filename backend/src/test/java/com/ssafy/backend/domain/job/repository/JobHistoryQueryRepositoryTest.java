package com.ssafy.backend.domain.job.repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.TestConfig;
import com.ssafy.backend.domain.entity.JobHistory;
import com.ssafy.backend.domain.entity.JobPosting;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.user.repository.UserRepository;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestConfig.class)
class JobHistoryQueryRepositoryTest {

	private JobHistoryQueryRepository jobHistoryQueryRepository;

	@Autowired
	private JobHistoryRepository jobHistoryRepository;
	@Autowired
	private JobPostingRepository jobPostingRepository;
	@Autowired
	private UserRepository userRepository;
	// @Autowired
	private JPAQueryFactory queryFactory;

	@Autowired
	private TestEntityManager testEntityManager;
	private EntityManager entityManager;
	
	@BeforeEach
	void setup() {

		entityManager = testEntityManager.getEntityManager();
		queryFactory = new JPAQueryFactory(entityManager);

		jobHistoryQueryRepository = new JobHistoryQueryRepository(queryFactory);

		userRepository.save(createUser("testNickname", "testImage", "testBojId", false));

		List<JobPosting> jobPostingList = new ArrayList<>();
		jobPostingList.add(createJobPosting("testCompanyName", "testName", false));
		jobPostingList.add(createJobPosting("testCompanyName", "testName", false));
		jobPostingList.add(createJobPosting("testCompanyName", "testName", false));
		jobPostingList.add(createJobPosting("testCompanyName", "testName", false));

		jobPostingRepository.saveAll(jobPostingList);

		List<JobHistory> jobHistoryList = new ArrayList<>();
		JobHistory jobHistory1 = createJobHistory(1L, "testDayName", "testMemo", 1L, "testObjective1", false,
			userRepository.findById(1L).get(),
			jobPostingRepository.findById(1L).get());

		JobHistory jobHistory2 = createJobHistory(2L, "testDayName", "testMemo", 1L, "testObjective2", false,
			userRepository.findById(1L).get(),
			jobPostingRepository.findById(1L).get());

		JobHistory jobHistory3 = createJobHistory(3L, "testDayName", "testMemo", 1L, "testObjective3", false,
			userRepository.findById(1L).get(),
			jobPostingRepository.findById(1L).get());

		JobHistory jobHistory4 = createJobHistory(4L, "testDayName", "testMemo", 1L, "testObjective4", false,
			userRepository.findById(1L).get(),
			jobPostingRepository.findById(1L).get());

		jobHistoryList.add(jobHistory1);
		jobHistoryList.add(jobHistory2);
		jobHistoryList.add(jobHistory3);
		jobHistoryList.add(jobHistory4);

		jobHistoryRepository.saveAll(jobHistoryList);

	}

	@AfterEach
	void finished() {

		jobHistoryRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();
		jobPostingRepository.deleteAllInBatch();
		this.entityManager
			.createNativeQuery(
				"ALTER TABLE users ALTER COLUMN `id` RESTART                                                                     WITH 1")
			.executeUpdate();
		this.entityManager
			.createNativeQuery(
				"ALTER TABLE job_posting ALTER COLUMN `id` RESTART                                                                     WITH 1")
			.executeUpdate();
		this.entityManager
			.createNativeQuery(
				"ALTER TABLE job_history ALTER COLUMN `id` RESTART                                                                     WITH 1")
			.executeUpdate();
	}

	@Test
	@DisplayName("해당 공고에 지원한 취업이력 조회 - 해당 공고가 있는 경우")
	void findByPostingIdTest() {
		//given
		long jobPosting = 1L;

		//when
		List<JobHistory> jobHistoryList = jobHistoryQueryRepository.findByPostingId(jobPosting);

		//then
		Assertions.assertThat(jobHistoryList)
			.isNotNull()
			.hasSize(4)
			.extracting("jobObjective")
			.contains("testObjective1", "testObjective2", "testObjective3", "testObjective4");

	}

	@Test
	@DisplayName("해당 공고에 지원한 취업이력 조회 - 해당 공고가 없는 경우.")
	void findByPostingIdNotTest() {
		//given
		long jobPosting = 2L;

		//when
		List<JobHistory> jobHistoryList = jobHistoryQueryRepository.findByPostingId(jobPosting);

		//then
		Assertions.assertThat(jobHistoryList)
			.isNotNull()
			.isEmpty();

	}

	@Test
	@DisplayName("해당 유저가 등록한 취업 이력 조회 - 초기 요청(페이징 x)")
	void findByUserJoinPostingTest() {
		//given
		long userId = 1L;
		List<Long> statusIdList = Arrays.asList(1L);
		String nextDate = null;
		Long jobHistoryId = null;
		PageRequest pageable = PageRequest.of(0, 5);

		//when
		List<JobHistory> jobHistories1 = jobHistoryQueryRepository.findByUserJoinPosting(userId, statusIdList, nextDate,
			jobHistoryId, pageable); //결과가 있을때.

		//then
		Assertions.assertThat(jobHistories1)
			.isNotNull()
			.hasSize(4)
			.extracting("jobObjective")
			.contains("testObjective1", "testObjective2", "testObjective3", "testObjective4");

	}

	@Test
	@DisplayName("해당 유저가 등록한 취업 이력 조회 - 존재하지 않는 취업상태 조건일때")
	void findByUserJoinPostingNotTest() {
		//given
		long userId = 1L;
		List<Long> statusIdList = Arrays.asList(2L, 3L);
		String nextDate = null;
		Long jobHistoryId = null;
		PageRequest pageable = PageRequest.of(0, 5);

		//when
		List<JobHistory> jobHistories2 = jobHistoryQueryRepository.findByUserJoinPosting(userId, statusIdList,
			nextDate,
			jobHistoryId, pageable); //결과가 없을때

		//then
		Assertions.assertThat(jobHistories2)
			.isNotNull()
			.isEmpty();

	}

	@Test
	@DisplayName("해당 유저가 등록한 취업 이력 조회 - 페이징 조회")
	void findByUserJoinPostingPageableTest() {

		//given
		long userId = 1L;
		List<Long> statusIdList = null;
		String nextDate = "2023-04-21";
		Long jobHistoryId = 3L;
		PageRequest pageable = PageRequest.of(0, 5);

		//when
		List<JobHistory> jobHistories3 = jobHistoryQueryRepository.findByUserJoinPosting(userId, statusIdList, nextDate,
			jobHistoryId, pageable); //결과가 2개

		//then
		Assertions.assertThat(jobHistories3)
			.isNotNull()
			.hasSize(2)
			.extracting("jobObjective")
			.contains("testObjective3", "testObjective4");
	}

	@Test
	@DisplayName("취업 이력 상세 조회 - 이력이 있을 때")
	void findByIdJoinPostingTest() {
		//given
		long userId = 1L;
		long jobHistoryId = 1L;

		//when
		JobHistory jobHistory = jobHistoryQueryRepository.findByIdJoinPosting(userId, jobHistoryId).get();

		//then
		Assertions.assertThat(jobHistory)
			.isNotNull()
			.extracting("jobObjective")
			.isEqualTo("testObjective1");

	}

	@Test
	@DisplayName("취업 이력 상세 조회 - 이력이 없을때")
	void findByIdJoinPostingNotTest() {
		//given
		long userId = 1L;
		long jobHistoryId = 5L;

		//when
		JobHistory jobHistory = jobHistoryQueryRepository.findByIdJoinPosting(userId, jobHistoryId).orElse(null);

		//then
		Assertions.assertThat(jobHistory)
			.isNull();

	}

	@Test
	@DisplayName("해당 공고 지원자 수 - 공고가 있을때")
	void countUserTotalJobHistoryTest() {
		//given
		long jobPostingId = 1L;

		//when
		Long count = jobHistoryQueryRepository.countUserTotalJobHistory(jobPostingId);

		//then
		Assertions.assertThat(count)
			.isEqualTo(4);
	}

	@Test
	@DisplayName("해당 공고 지원자 수 - 공고가 없을 때")
	void countUserTotalJobHistoryNotTest() {
		//given
		long jobPostingId = 2L;

		//when
		Long count = jobHistoryQueryRepository.countUserTotalJobHistory(jobPostingId);

		//then
		Assertions.assertThat(count)
			.isEqualTo(0);

	}

	@Test
	@DisplayName("업데이트 벌크연산(삭제처리) - 삭제 값이 있을때")
	void deleteBulkTest() {
		//given
		long userId = 1L;
		List<Long> jobHistoryIds = Arrays.asList(1L, 2L);

		//when
		long count = jobHistoryQueryRepository.deleteBulk(userId, jobHistoryIds);

		//then
		Assertions.assertThat(count)
			.isEqualTo(2);

	}

	@Test
	@DisplayName("업데이트 벌크연산(삭제처리) - 삭제 값이 없을때")
	void deleteBulkNotTest() {
		//given
		long userId = 1L;
		List<Long> jobHistoryIds = Arrays.asList(5L, 6L);

		//when
		long count = jobHistoryQueryRepository.deleteBulk(userId, jobHistoryIds);

		//then
		Assertions.assertThat(count)
			.isEqualTo(0);
	}

	public JobHistory createJobHistory(long plus, String dDayName, String memo, long statusId,
		String jobObjective,
		boolean isDeleted, User user, JobPosting jobPosting) {

		LocalDate nowDate = LocalDate.parse("2023-04-19", DateTimeFormatter.ofPattern("yyyy-MM-dd")).plusDays(plus);

		return JobHistory.builder()
			.dDay(nowDate)
			.dDayName(dDayName)
			.memo(memo)
			.statusId(statusId)
			.jobObjective(jobObjective)
			.isDeleted(isDeleted)
			.user(user)
			.jobPosting(jobPosting)
			.build();
	}

	public User createUser(String nickname, String image, String bojId, boolean isDeleted) {
		return User.builder()
			.nickname(nickname)
			.image(image)
			.bojId(bojId)
			.isDeleted(isDeleted)
			.build();

	}

	public JobPosting createJobPosting(String companyName, String name, boolean isClose) {

		LocalDate nowDate = LocalDate.parse("2023-04-19", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate endDate = nowDate.plusDays(1);

		return JobPosting.builder()
			.companyName(companyName)
			.name(name)
			.startTime(nowDate)
			.endTime(endDate)
			.isClose(isClose)
			.build();
	}
}