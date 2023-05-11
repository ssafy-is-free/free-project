package com.ssafy.backend.domain.algorithm.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import com.ssafy.backend.domain.algorithm.dto.response.BojRankResponse;
import com.ssafy.backend.domain.algorithm.repository.BojLanguageRepository;
import com.ssafy.backend.domain.algorithm.repository.BojRepository;
import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.BaekjoonLanguage;
import com.ssafy.backend.domain.entity.JobHistory;
import com.ssafy.backend.domain.entity.JobPosting;
import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.entity.common.LanguageType;
import com.ssafy.backend.domain.job.repository.JobHistoryRepository;
import com.ssafy.backend.domain.job.repository.JobPostingRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.util.repository.LanguageRepository;

@SpringBootTest
public class AlgorithmServiceAllRankTest {
	@Autowired
	private BojRepository bojRepository;
	@Autowired
	private BojLanguageRepository bojLanguageRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LanguageRepository languageRepository;
	@Autowired
	private JobPostingRepository jobPostingRepository;
	@Autowired
	private JobHistoryRepository jobHistoryRepository;
	@Autowired
	private AlgorithmServiceImpl algorithmService;

	@AfterEach
	void tearDown() {
		languageRepository.deleteAllInBatch();
		bojLanguageRepository.deleteAllInBatch();
		bojRepository.deleteAllInBatch();
		jobHistoryRepository.deleteAllInBatch();
		jobPostingRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("백준 전체 조회 정상 테스트")
	public void getBojRankListByBojIdTest() {
		//given
		User user1 = createUser("user1", "user1");
		User user2 = createUser("user2", "user2");
		User user3 = createUser("user3", "user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Baekjoon boj1 = createBaekjoon(user1, 14, 275, 9, 723, 73,
			100);
		Baekjoon boj2 = createBaekjoon(user2, 15, 278, 5, 700,
			193, 200);
		Baekjoon boj3 = createBaekjoon(user3, 13, 280, 12, 623,
			173, 300);
		bojRepository.saveAll(Arrays.asList(boj1, boj2, boj3));

		Language language1 = createLanguage("Java 11");
		Language language2 = createLanguage("C++17");
		Language language3 = createLanguage("Python3");
		languageRepository.saveAll(Arrays.asList(language1, language2, language3));

		BaekjoonLanguage baekjoonLanguage = createBaekjoonLanguage(language1.getId(), "50.00", 20, boj1);
		bojLanguageRepository.save(baekjoonLanguage);

		JobPosting jobPosting1 = createJobPosting("정승네트워크", "자바 4명~~");
		jobPostingRepository.save(jobPosting1);

		JobHistory jobHistory1 = createJobHistory(user1, jobPosting1);
		JobHistory jobHistory2 = createJobHistory(user2, jobPosting1);
		jobHistoryRepository.saveAll(Arrays.asList(jobHistory1, jobHistory2));

		//when
		List<BojRankResponse> responses = algorithmService.getBojRankListByBojId(null, language1.getId(), null, null,
			null,
			jobPosting1.getId(),
			Pageable.ofSize(10));

		//then
		assertThat(responses).hasSize(1);

	}

	@Test
	@DisplayName("백준 전체 조회 공고 X 테스트")
	public void getBojRankListByBojIdJobNullTest() {
		//given
		User user1 = createUser("user1", "user1");
		User user2 = createUser("user2", "user2");
		User user3 = createUser("user3", "user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Baekjoon boj1 = createBaekjoon(user1, 14, 275, 9, 723, 73,
			100);
		Baekjoon boj2 = createBaekjoon(user2, 15, 278, 5, 700,
			193, 200);
		Baekjoon boj3 = createBaekjoon(user3, 13, 280, 12, 623,
			173, 300);
		bojRepository.saveAll(Arrays.asList(boj1, boj2, boj3));

		Language language1 = createLanguage("Java 11");
		Language language2 = createLanguage("C++17");
		Language language3 = createLanguage("Python3");
		languageRepository.saveAll(Arrays.asList(language1, language2, language3));

		BaekjoonLanguage baekjoonLanguage1 = createBaekjoonLanguage(language1.getId(), "50.00", 20, boj1);
		BaekjoonLanguage baekjoonLanguage2 = createBaekjoonLanguage(language1.getId(), "50.00", 20, boj2);
		bojLanguageRepository.saveAll(Arrays.asList(baekjoonLanguage1, baekjoonLanguage2));

		//when
		List<BojRankResponse> responses = algorithmService.getBojRankListByBojId(null, language1.getId(), null, null,
			null, null, Pageable.ofSize(10));

		//then
		assertThat(responses).hasSize(2);

	}

	@Test
	@DisplayName("백준 전체 조회 언어 X 공고 X 테스트")
	public void getBojRankListByBojIdLanguageAndJobNullTest() {
		//given
		User user1 = createUser("user1", "user1");
		User user2 = createUser("user2", "user2");
		User user3 = createUser("user3", "user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Baekjoon boj1 = createBaekjoon(user1, 14, 275, 9, 723, 73,
			100);
		Baekjoon boj2 = createBaekjoon(user2, 15, 278, 5, 700,
			193, 200);
		Baekjoon boj3 = createBaekjoon(user3, 13, 280, 12, 623,
			173, 300);
		bojRepository.saveAll(Arrays.asList(boj1, boj2, boj3));

		Language language1 = createLanguage("Java 11");
		Language language2 = createLanguage("C++17");
		Language language3 = createLanguage("Python3");
		languageRepository.saveAll(Arrays.asList(language1, language2, language3));

		BaekjoonLanguage baekjoonLanguage = createBaekjoonLanguage(language1.getId(), "50.00", 20, boj3);
		bojLanguageRepository.save(baekjoonLanguage);

		JobPosting jobPosting1 = createJobPosting("정승네트워크", "자바 4명~~");
		JobPosting jobPosting2 = createJobPosting("정승네트워크2", "자바 4명~~2");
		jobPostingRepository.saveAll(Arrays.asList(jobPosting1, jobPosting2));

		JobHistory jobHistory2 = createJobHistory(user2, jobPosting1);
		jobHistoryRepository.saveAll(Arrays.asList(jobHistory2));

		//when
		List<BojRankResponse> responses = algorithmService.getBojRankListByBojId(null, language2.getId(), null, null,
			null, jobPosting2.getId(), Pageable.ofSize(10));

		//then
		assertThat(responses).hasSize(0);

	}

	@Test
	@DisplayName("백준 전체 조회 언어,공고 Empty 테스트")
	public void getBojRankListByBojIdLanguageAndJobEmptyTest() {
		//given
		User user1 = createUser("user1", "user1");
		User user2 = createUser("user2", "user2");
		User user3 = createUser("user3", "user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Baekjoon boj1 = createBaekjoon(user1, 14, 275, 9, 723, 73,
			100);
		Baekjoon boj2 = createBaekjoon(user2, 15, 278, 5, 700,
			193, 200);
		Baekjoon boj3 = createBaekjoon(user3, 13, 280, 12, 623,
			173, 300);
		bojRepository.saveAll(Arrays.asList(boj1, boj2, boj3));

		//when
		List<BojRankResponse> responses = algorithmService.getBojRankListByBojId(null, null, null, null,
			null, null, Pageable.ofSize(10));

		//then
		assertThat(responses).hasSize(3);

	}

	@Test
	@DisplayName("백준 전체 조회 공고id가 있는데, 공고에 해당하는 유저가 없는 경우 테스트")
	public void getBojRankListByBojIdJobNotNullUserNullTest() {
		//given
		User user1 = createUser("user1", "user1");
		User user2 = createUser("user2", "user2");
		User user3 = createUser("user3", "user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Baekjoon boj1 = createBaekjoon(user1, 14, 275, 9, 723, 73,
			100);
		Baekjoon boj2 = createBaekjoon(user2, 15, 278, 5, 700,
			193, 200);
		Baekjoon boj3 = createBaekjoon(user3, 13, 280, 12, 623,
			173, 300);
		bojRepository.saveAll(Arrays.asList(boj1, boj2, boj3));
		JobPosting jobPosting1 = createJobPosting("정승네트워크", "자바 4명~~");
		jobPostingRepository.save(jobPosting1);

		//when
		List<BojRankResponse> responses = algorithmService.getBojRankListByBojId(null, null, null, null,
			null, jobPosting1.getId(), Pageable.ofSize(10));

		//then
		assertThat(responses).isEmpty();

	}

	@Test
	@DisplayName("백준 전체 조회 언어id가 있는데, 언어에 해당하는 유저가 없는 경우 테스트")
	public void getBojRankListByBojIdLanguageNotNullUserNullTest() {
		//given
		User user1 = createUser("user1", "user1");
		User user2 = createUser("user2", "user2");
		User user3 = createUser("user3", "user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Baekjoon boj1 = createBaekjoon(user1, 14, 275, 9, 723, 73,
			100);
		Baekjoon boj2 = createBaekjoon(user2, 15, 278, 5, 700,
			193, 200);
		Baekjoon boj3 = createBaekjoon(user3, 13, 280, 12, 623,
			173, 300);
		bojRepository.saveAll(Arrays.asList(boj1, boj2, boj3));

		Language language1 = createLanguage("Java 11");
		Language language2 = createLanguage("C++17");
		Language language3 = createLanguage("Python3");
		languageRepository.saveAll(Arrays.asList(language1, language2, language3));

		//when
		List<BojRankResponse> responses = algorithmService.getBojRankListByBojId(null, language1.getId(), null, null,
			null, null, Pageable.ofSize(10));

		//then
		assertThat(responses).isEmpty();

	}

	@Test
	@DisplayName("백준 전체 조회 스코어 존재 유저아이디 X인 경우 정렬 테스트")
	public void getBojRankListByBojIdScoreUserIdNullTest() {
		//given
		User user1 = createUser("user1", "user1");
		User user2 = createUser("user2", "user2");
		User user3 = createUser("user3", "user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Baekjoon boj1 = createBaekjoon(user1, 14, 275, 9, 723, 73,
			100);
		Baekjoon boj2 = createBaekjoon(user2, 15, 278, 5, 700,
			193, 200);
		Baekjoon boj3 = createBaekjoon(user3, 13, 280, 12, 623,
			173, 300);
		bojRepository.saveAll(Arrays.asList(boj1, boj2, boj3));

		//when
		List<BojRankResponse> responses = algorithmService.getBojRankListByBojId(null, null, 200, null,
			null, null, Pageable.ofSize(10));

		//then
		assertThat(responses).hasSize(3);

	}

	@Test
	@DisplayName("백준 전체 조회 스코어 기반 정렬 테스트")
	public void getBojRankListByBojIdScoreTest() {
		//given
		User user1 = createUser("user1", "user1");
		User user2 = createUser("user2", "user2");
		User user3 = createUser("user3", "user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Baekjoon boj1 = createBaekjoon(user1, 14, 275, 9, 723, 73,
			100);
		Baekjoon boj2 = createBaekjoon(user2, 15, 278, 5, 700,
			193, 200);
		Baekjoon boj3 = createBaekjoon(user3, 13, 280, 12, 623,
			173, 300);
		bojRepository.saveAll(Arrays.asList(boj1, boj2, boj3));

		//when
		List<BojRankResponse> responses = algorithmService.getBojRankListByBojId(null, null, 200, null,
			user2.getId(), null, Pageable.ofSize(10));

		//then
		assertThat(responses).hasSize(1);

	}

	private BaekjoonLanguage createBaekjoonLanguage(long languageId, String passPercentage, int passCount,
		Baekjoon boj) {
		return BaekjoonLanguage.builder()
			.languageId(languageId)
			.passPercentage(passPercentage)
			.passCount(passCount)
			.baekjoon(boj)
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
			.startTime(LocalDate.now())
			.endTime(LocalDate.now())
			.isClose(false)
			.build();

	}

	private Language createLanguage(String name) {
		return Language.builder()
			.name(name)
			.type(LanguageType.BAEKJOON)
			.build();
	}

	private User createUser(String nickname, String bojId) {
		return User.builder().nickname(nickname).bojId(bojId).image("1").isDeleted(false).build();
	}

	private Baekjoon createBaekjoon(User user, int tier, int passCount, int tryFailCount, int submitCount,
		int failCount, int score) {
		return Baekjoon.builder()
			.user(user)
			.tier(tier)
			.passCount(passCount)
			.tryFailCount(tryFailCount)
			.submitCount(submitCount)
			.failCount(failCount)
			.score(score)
			.build();
	}
}
