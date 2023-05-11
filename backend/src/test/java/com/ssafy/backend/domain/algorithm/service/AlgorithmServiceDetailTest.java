package com.ssafy.backend.domain.algorithm.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.backend.domain.algorithm.dto.response.BojInfoDetailResponse;
import com.ssafy.backend.domain.algorithm.repository.BojLanguageRepository;
import com.ssafy.backend.domain.algorithm.repository.BojRepository;
import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.BaekjoonLanguage;
import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.entity.common.LanguageType;
import com.ssafy.backend.domain.job.repository.JobHistoryRepository;
import com.ssafy.backend.domain.job.repository.JobPostingRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.util.repository.LanguageRepository;

@SpringBootTest
public class AlgorithmServiceDetailTest {
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
	@DisplayName("백준 상세정보 정상 작동 테스트")
	public void GetBojInfoDetailByUserIdTest() {
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

		//when
		BojInfoDetailResponse response = algorithmService.getBojInfoDetailByUserId(user1.getId());

		//then
		assertThat(response.getBojId()).isEqualTo(user1.getBojId());

	}

	@Test
	@DisplayName("백준 상세정보 유저 테이블에 백준 아이디는 있는데 백준 테이블에 정보가 없는 경우 테스트")
	public void GetBojInfoDetailByUserIdBojNullTest() {
		//given
		User user1 = createUser("user1", "user1");
		User user2 = createUser("user2", "user2");
		User user3 = createUser("user3", "user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));
		//when
		BojInfoDetailResponse response = algorithmService.getBojInfoDetailByUserId(user1.getId());

		//then
		assertThat(response.checkForNull()).isTrue();

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
