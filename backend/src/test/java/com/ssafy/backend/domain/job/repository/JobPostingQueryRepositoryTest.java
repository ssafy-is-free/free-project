package com.ssafy.backend.domain.job.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ssafy.backend.domain.entity.JobPosting;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class JobPostingQueryRepositoryTest {

	@Autowired
	private JobPostingQueryRepository jobPostingQueryRepository;
	@Autowired
	private JobPostingRepository jobPostingRepository;

	@Autowired
	private JobStatusRepository jobStatusRepository;

	@Test
	@Transactional
	@DisplayName("취업 공고 조회 테스트")
	public void jobPostingTest() {
		//give
		LocalDateTime testTime = LocalDateTime.now();

		JobPosting jobPosting1 = JobPosting.create("삼성전자", "삼성그룹 2023년 신입공채", testTime, testTime, false);
		JobPosting jobPosting2 = JobPosting.create("삼성전기", "삼성그룹 2022년 신입공채", testTime, testTime, false);
		JobPosting jobPosting3 = JobPosting.create("네이버", "네이버 2023년 신입공채", testTime, testTime, false);
		JobPosting jobPosting4 = JobPosting.create("카카오", "카카오 2023년 신입공채", testTime, testTime, false);

		List<JobPosting> jobPostingArrayList = new ArrayList<>(
			Arrays.asList(jobPosting1, jobPosting2, jobPosting3, jobPosting4));

		jobPostingRepository.saveAll(jobPostingArrayList);

		//when
		List<JobPosting> jobPostingList = jobPostingQueryRepository.findByNameLikeAndIsCloseFalse(null);

		//then
		Assertions.assertThat(jobPostingList)
			.isNotEmpty()
			.hasSize(4)
			.extracting("name").contains("삼성그룹 2023년 신입공채", "삼성그룹 2022년 신입공채", "네이버 2023년 신입공채", "카카오 2023년 신입공채");

	}

	@Test
	@Transactional
	@DisplayName("취업 공고 키워드 조회 테스트")
	public void jobPostingKeywordTest() {

	}

	@Test
	@Transactional
	@DisplayName("취업 상태 전체 조회")
	public void jobStatusTest() {

	}

}