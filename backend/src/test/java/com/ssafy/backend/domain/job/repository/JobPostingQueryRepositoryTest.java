package com.ssafy.backend.domain.job.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.TestConfig;
import com.ssafy.backend.domain.entity.JobPosting;
import com.ssafy.backend.domain.entity.JobStatus;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestConfig.class)
class JobPostingQueryRepositoryTest {

	private JobPostingQueryRepository jobPostingQueryRepository;
	@Autowired
	private JobPostingRepository jobPostingRepository;
	@Autowired
	private JobStatusRepository jobStatusRepository;

	//@Autowired
	private JPAQueryFactory queryFactory;

	@Autowired
	private TestEntityManager testEntityManager;
	private EntityManager entityManager;

	@BeforeEach
	void setup() {
		entityManager = testEntityManager.getEntityManager();
		queryFactory = new JPAQueryFactory(entityManager);

		jobPostingQueryRepository = new JobPostingQueryRepository(queryFactory);
	}

	@Test
	@Transactional
	@DisplayName("취업 공고 조회 테스트")
	public void jobPostingTest() {
		//give
		LocalDate testTime = LocalDate.now();

		JobPosting jobPosting1 = createJobPosting(testTime, "삼성전자", "삼성그룹 2023년 신입공채", false);
		JobPosting jobPosting2 = createJobPosting(testTime, "삼성전기", "삼성그룹 2022년 신입공채", false);
		JobPosting jobPosting3 = createJobPosting(testTime, "네이버", "네이버 2023년 신입공채", false);
		JobPosting jobPosting4 = createJobPosting(testTime, "카카오", "카카오 2023년 신입공채", false);

		List<JobPosting> jobPostingArrayList = new ArrayList<>(
			Arrays.asList(jobPosting1, jobPosting2, jobPosting3, jobPosting4));

		jobPostingRepository.saveAll(jobPostingArrayList);

		//when
		List<JobPosting> jobPostingList = jobPostingQueryRepository.findByNameLikeAndIsCloseFalse(null);

		// then
		Assertions.assertThat(jobPostingList)
			.isNotEmpty()
			.hasSize(4)
			.extracting("name").contains("삼성그룹 2023년 신입공채", "삼성그룹 2022년 신입공채", "네이버 2023년 신입공채", "카카오 2023년 신입공채");

	}

	@Test
	@Transactional
	@DisplayName("취업 공고 키워드 조회 테스트")
	public void jobPostingKeywordTest() {

		//give
		LocalDate testTime = LocalDate.now();

		JobPosting jobPosting1 = createJobPosting(testTime, "삼성전자", "삼성그룹 2023년 신입공채", false);
		JobPosting jobPosting2 = createJobPosting(testTime, "삼성전기", "삼성그룹 2022년 신입공채", false);
		JobPosting jobPosting3 = createJobPosting(testTime, "네이버", "네이버 2023년 채용", false);
		JobPosting jobPosting4 = createJobPosting(testTime, "카카오", "카카오 2023년 신입공채", false);

		List<JobPosting> jobPostingArrayList = new ArrayList<>(
			Arrays.asList(jobPosting1, jobPosting2, jobPosting3, jobPosting4));

		jobPostingRepository.saveAll(jobPostingArrayList);

		//when
		List<JobPosting> jobPostingList1 = jobPostingQueryRepository.findByNameLikeAndIsCloseFalse("삼성"); //2
		List<JobPosting> jobPostingList2 = jobPostingQueryRepository.findByNameLikeAndIsCloseFalse("년"); //4
		List<JobPosting> jobPostingList3 = jobPostingQueryRepository.findByNameLikeAndIsCloseFalse("공채"); // 3

		//then
		Assertions.assertThat(jobPostingList1)
			.isNotEmpty()
			.hasSize(2)
			.extracting("name").contains("삼성그룹 2023년 신입공채", "삼성그룹 2022년 신입공채");

		Assertions.assertThat(jobPostingList2)
			.isNotEmpty()
			.hasSize(4)
			.extracting("name").contains("삼성그룹 2023년 신입공채", "삼성그룹 2022년 신입공채", "네이버 2023년 채용", "카카오 2023년 신입공채");

		Assertions.assertThat(jobPostingList3)
			.isNotEmpty()
			.hasSize(3)
			.extracting("name").contains("삼성그룹 2023년 신입공채", "삼성그룹 2022년 신입공채", "카카오 2023년 신입공채");
	}

	@Test
	@Transactional
	@DisplayName("취업 상태 전체 조회")
	public void jobStatusTest() {

		//given
		JobStatus jobStatus1 = createJobStatus("면접 불합격");
		JobStatus jobStatus2 = createJobStatus("서류 합격");
		JobStatus jobStatus3 = createJobStatus("서류 불합격");
		JobStatus jobStatus4 = createJobStatus("최종 합격");

		ArrayList<JobStatus> jobStatusArrrayList = new ArrayList<>(
			Arrays.asList(jobStatus1, jobStatus2, jobStatus3, jobStatus4));

		jobStatusRepository.saveAll(jobStatusArrrayList);

		//when
		List<JobStatus> jobStatusList = jobStatusRepository.findAll();

		//then
		Assertions.assertThat(jobStatusList)
			.isNotEmpty()
			.hasSize(4)
			.extracting("name").contains("면접 불합격", "서류 합격", "서류 불합격", "최종 합격");

	}

	private JobPosting createJobPosting(LocalDate currentTime, String companyName,
		String name, boolean isClose) {

		return JobPosting.builder()
			.companyName(companyName)
			.name(name)
			.startTime(currentTime)
			.endTime(currentTime)
			.isClose(isClose).build();

	}

	private JobStatus createJobStatus(String name) {

		return JobStatus.builder()
			.name(name).build();
	}

}