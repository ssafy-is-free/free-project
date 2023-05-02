package com.ssafy.backend.domain.github.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.JobHistory;
import com.ssafy.backend.domain.entity.JobPosting;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.github.dto.GitHubRankingFilter;
import com.ssafy.backend.domain.github.dto.GithubRankingCover;
import com.ssafy.backend.domain.github.dto.GithubRankingResponse;
import com.ssafy.backend.domain.github.repository.GithubRepository;
import com.ssafy.backend.domain.job.repository.JobHistoryRepository;
import com.ssafy.backend.domain.job.repository.JobPostingRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.global.response.exception.CustomException;

@SpringBootTest
class GithubRankingServiceTest {

	@Autowired
	private GithubRankingService githubRankingService;
	@Autowired
	private JobPostingRepository jobPostingRepository;
	@Autowired
	private JobHistoryRepository jobHistoryRepository;
	@Autowired
	private GithubRepository githubRepository;
	@Autowired
	private UserRepository userRepository;

	@AfterEach
	void tearDown() {
		jobHistoryRepository.deleteAllInBatch();
		jobPostingRepository.deleteAllInBatch();
		githubRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();

	}

	@DisplayName("공고별로 깃허브 랭킹을 조회할 수 있다.")
	@Test
	void getGithubRankByJobPosting() {
		//given
		User user1 = createUser("user1");
		User user2 = createUser("user2");
		User user3 = createUser("user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Github github1 = createGithub(user1, 100);
		Github github2 = createGithub(user2, 200);
		Github github3 = createGithub(user3, 200);
		githubRepository.saveAll(Arrays.asList(github1, github2, github3));

		JobPosting jobPosting1 = createJobPosting("정승네트워크", "자바 4명~~");
		jobPostingRepository.save(jobPosting1);

		JobHistory jobHistory1 = createJobHistory(user1, jobPosting1);
		JobHistory jobHistory2 = createJobHistory(user2, jobPosting1);
		jobHistoryRepository.saveAll(Arrays.asList(jobHistory1, jobHistory2));

		Long jobPostingId = jobPostingRepository.findByName("자바 4명~~").get().getId();
		GitHubRankingFilter rankingFilter = GitHubRankingFilter.builder().build();
		Pageable pageable = Pageable.ofSize(2);

		//when
		GithubRankingResponse response = githubRankingService.getGithubRank(null, null, null, rankingFilter,
			jobPostingId, pageable);

		//then
		assertThat(response.getRanks()).hasSize(2)
			.extracting(GithubRankingCover::getNickname)
			.containsExactly("user2", "user1");

	}

	@DisplayName("잘못된 공고로 깃허브 랭킹을 조회하면 예외를 터뜨린다.")
	@Test
	void getGithubRankByInCorrectJobPosting() {
		//given
		User user1 = createUser("user1");
		User user2 = createUser("user2");
		User user3 = createUser("user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Github github1 = createGithub(user1, 100);
		Github github2 = createGithub(user2, 200);
		Github github3 = createGithub(user3, 200);
		githubRepository.saveAll(Arrays.asList(github1, github2, github3));

		JobPosting jobPosting1 = createJobPosting("정승네트워크", "자바 4명~~");
		jobPostingRepository.save(jobPosting1);

		JobHistory jobHistory1 = createJobHistory(user1, jobPosting1);
		JobHistory jobHistory2 = createJobHistory(user2, jobPosting1);
		jobHistoryRepository.saveAll(Arrays.asList(jobHistory1, jobHistory2));

		Long jobPostingId = 2L;
		GitHubRankingFilter rankingFilter = GitHubRankingFilter.builder().build();
		Pageable pageable = Pageable.ofSize(2);

		//when //then
		assertThatThrownBy(() -> githubRankingService.getGithubRank(null, null, null, rankingFilter, jobPostingId,
			pageable)).isInstanceOf(CustomException.class);

	}

	private User createUser(String nickname) {
		return User.builder().nickname(nickname).image("1").isDeleted(false).build();
	}

	private Github createGithub(User user, Integer score) {
		return Github.builder()
			.user(user)
			.commitTotalCount(1)
			.followerTotalCount(1)
			.starTotalCount(1)
			.score(score)
			.profileLink("1")
			.previousRank(1)
			.build();
	}

	private JobHistory createJobHistory(User user, JobPosting jobPosting) {
		return JobHistory.builder()
			.dDay(LocalDate.now())
			.dDayName("test")
			.statusId(1)
			.jobObjective("백엔드 개발")
			.isDeleted(false)
			.user(user)
			.jobPosting(jobPosting)
			.build();
	}

	private JobPosting createJobPosting(String companyName, String name) {
		return JobPosting.builder()
			.companyName(companyName)
			.name(name)
			.startTime(LocalDateTime.now())
			.endTime(LocalDateTime.now())
			.isClose(false)
			.build();

	}

}