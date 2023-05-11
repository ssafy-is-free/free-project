package com.ssafy.backend.domain.algorithm.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
import com.ssafy.backend.domain.user.dto.NicknameListResponse;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.util.repository.LanguageRepository;

@SpringBootTest
public class AlgorithmServiceTest {
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
	@DisplayName("유저 마이 랭킹 정보 정상 작동 테스트")
	public void GetBojByUserIdTest() {
		//given

		User user1 = createUser("user1", "user1");
		User user2 = createUser("user2", "user2");
		User user3 = createUser("user3", "user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Baekjoon boj1 = createBaekjoon(user1, 14, 275, 9, 723, 73,
			100);
		Baekjoon boj2 = createBaekjoon(user2, 16, 278, 5, 700,
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
		BojRankResponse response = algorithmService.getBojByUserId(user1.getId(), language1.getId(),
			jobPosting1.getId());

		//then
		Assertions.assertNotNull(response);
		assertThat(response.getUserId()).isEqualTo(user1.getId());

	}

	@Test
	@DisplayName("유저 마이 랭킹 정보 언어id가 있지만 언어에 해당하는 유저 없는 경우 테스트")
	public void GetBojByUserIdLanguageUserNullTest() {
		//given

		User user1 = createUser("user1", "user1");
		User user2 = createUser("user2", "user2");
		User user3 = createUser("user3", "user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Baekjoon boj1 = createBaekjoon(user1, 14, 275, 9, 723, 73,
			100);
		Baekjoon boj2 = createBaekjoon(user2, 16, 278, 5, 700,
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

		//when
		BojRankResponse response = algorithmService.getBojByUserId(user1.getId(), language2.getId(), null);

		//then
		assertThat(response.checkForNull()).isTrue();

	}

	@Test
	@DisplayName("유저 마이 랭킹 정보 공고id는 있지만 공고에 해당하는 유저가 없는 경우 테스트")
	public void GetBojByUserIdJobUserNullTest() {
		//given

		User user1 = createUser("user1", "user1");
		User user2 = createUser("user2", "user2");
		User user3 = createUser("user3", "user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Baekjoon boj1 = createBaekjoon(user1, 14, 275, 9, 723, 73,
			100);
		Baekjoon boj2 = createBaekjoon(user2, 16, 278, 5, 700,
			193, 200);
		Baekjoon boj3 = createBaekjoon(user3, 13, 280, 12, 623,
			173, 300);
		bojRepository.saveAll(Arrays.asList(boj1, boj2, boj3));

		JobPosting jobPosting1 = createJobPosting("정승네트워크", "자바 4명~~");
		jobPostingRepository.save(jobPosting1);

		JobHistory jobHistory1 = createJobHistory(user1, jobPosting1);
		JobHistory jobHistory2 = createJobHistory(user2, jobPosting1);
		jobHistoryRepository.saveAll(Arrays.asList(jobHistory1, jobHistory2));

		//when
		BojRankResponse response = algorithmService.getBojByUserId(user3.getId(), null, jobPosting1.getId());

		//then
		assertThat(response.checkForNull()).isTrue();

	}

	@Test
	@DisplayName("유저 마이 랭킹 공고 필터 없는 경우 테스트")
	public void GetBojByUserIdLanguageNullTest() {
		//given

		User user1 = createUser("user1", "user1");
		User user2 = createUser("user2", "user2");
		User user3 = createUser("user3", "user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Baekjoon boj1 = createBaekjoon(user1, 14, 275, 9, 723, 73,
			100);
		Baekjoon boj2 = createBaekjoon(user2, 16, 278, 5, 700,
			193, 200);
		Baekjoon boj3 = createBaekjoon(user3, 13, 280, 12, 623,
			173, 300);
		bojRepository.saveAll(Arrays.asList(boj1, boj2, boj3));

		//when
		BojRankResponse response = algorithmService.getBojByUserId(user1.getId(), null, null);

		//then
		assertThat(response.getUserId()).isEqualTo(user1.getId());

	}

	@Test
	@DisplayName("유저 마이 랭킹 삭제된 유저인 경우 테스트")
	public void GetBojByUserIdUserDeleteTest() {
		//given

		User user1 = createUser("user1", "user1", true);
		userRepository.saveAll(Arrays.asList(user1));

		Baekjoon boj1 = createBaekjoon(user1, 14, 275, 9, 723, 73,
			100);
		bojRepository.saveAll(Arrays.asList(boj1));

		//when
		BojRankResponse response = algorithmService.getBojByUserId(user1.getId(), null, null);

		//then
		assertThat(response.checkForNull()).isTrue();

	}

	@Test
	@DisplayName("유저 마이 랭킹 백준 아아디가 없는 유저의 경우")
	public void GetBojByUserIdUserBojIdNullTest() {
		//given
		User user1 = createUser("user1");
		userRepository.saveAll(Arrays.asList(user1));

		//when
		BojRankResponse response = algorithmService.getBojByUserId(user1.getId(), null, null);

		//then
		assertThat(response.checkForNull()).isTrue();

	}

	@Test
	@DisplayName("유저 마이 랭킹 백준 아아디가 이상한 유저의 경우")
	public void GetBojByUserIdUserBojIdWeirdTest() {
		//given
		User user1 = createUser("user1", "Weird");
		userRepository.saveAll(Arrays.asList(user1));

		//when
		BojRankResponse response = algorithmService.getBojByUserId(user1.getId(), null, null);

		//then
		assertThat(response.checkForNull()).isTrue();

	}

	@Test
	@DisplayName("유저 마이 랭킹 언어 필터 없는 경우 테스트")
	public void GetBojByUserIdJobNullTest() {
		//given

		User user1 = createUser("user1", "user1");
		User user2 = createUser("user2", "user2");
		User user3 = createUser("user3", "user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Baekjoon boj1 = createBaekjoon(user1, 14, 275, 9, 723, 73,
			100);
		Baekjoon boj2 = createBaekjoon(user2, 16, 278, 5, 700,
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

		//when
		BojRankResponse response = algorithmService.getBojByUserId(user1.getId(), language1.getId(), null);

		//then
		assertThat(response.getUserId()).isEqualTo(user1.getId());

	}

	@Test
	@DisplayName("유저 백준 닉네임 중복 확인하는 테스트")
	public void GetBojListByBojIdDistinctTest() {
		//given
		User user1 = createUser("user1", "백1");
		User user2 = createUser("user2", "백2");
		User user3 = createUser("user3", "백3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		//when
		List<NicknameListResponse> response = algorithmService.getBojListByBojId("백");

		//then
		assertThat(response).hasSize(3);
	}

	@Test
	@DisplayName("유저 백준 닉네임 중간 닉네임 테스트")
	public void GetBojListByBojIdTest() {
		//given
		User user1 = createUser("user1", "백1");
		User user2 = createUser("user2", "백2");
		User user3 = createUser("user3", "백3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		//when
		List<NicknameListResponse> response = algorithmService.getBojListByBojId("1");

		//then
		assertThat(response).hasSize(1).extracting(NicknameListResponse::getNickname).containsExactly("백1");
	}

	@Test
	@DisplayName("유저 백준 닉네임 정확도 테스트")
	public void GetBojListByBojIdCheckTest() {
		//given
		User user1 = createUser("user1", "백1");
		User user2 = createUser("user2", "백2");
		User user3 = createUser("user3", "백3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		//when
		List<NicknameListResponse> response = algorithmService.getBojListByBojId("백1");

		//then
		assertThat(response.get(0)).extracting(NicknameListResponse::getNickname, NicknameListResponse::getUserId)
			.containsExactly("백1", user1.getId());
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

	private User createUser(String nickname) {
		return User.builder().nickname(nickname).image("1").isDeleted(false).build();
	}

	private User createUser(String nickname, String bojId) {
		return User.builder().nickname(nickname).bojId(bojId).image("1").isDeleted(false).build();
	}

	private User createUser(String nickname, String bojId, boolean isDeleted) {
		return User.builder().nickname(nickname).bojId(bojId).image("1").isDeleted(isDeleted).build();
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
