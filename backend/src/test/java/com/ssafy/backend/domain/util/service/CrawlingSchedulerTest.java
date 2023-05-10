package com.ssafy.backend.domain.util.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.backend.domain.algorithm.repository.BojLanguageRepository;
import com.ssafy.backend.domain.algorithm.repository.BojRepository;
import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.entity.common.LanguageType;
import com.ssafy.backend.domain.github.repository.GithubRepository;
import com.ssafy.backend.domain.github.service.GithubRankingService;
import com.ssafy.backend.domain.job.repository.JobHistoryRepository;
import com.ssafy.backend.domain.job.repository.JobPostingRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.util.repository.LanguageRepository;

@SpringBootTest
public class CrawlingSchedulerTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BojRepository bojRepository;
	@Autowired
	private LanguageRepository languageRepository;
	@Autowired
	private BojLanguageRepository bojLanguageRepository;
	@Autowired
	private CrawlingScheduler crawlingScheduler;
	@Autowired
	private GithubRankingService githubRankingService;
	@Autowired
	private JobPostingRepository jobPostingRepository;
	@Autowired
	private JobHistoryRepository jobHistoryRepository;
	@Autowired
	private GithubRepository githubRepository;

	@AfterEach
	void tearDown() {
		languageRepository.deleteAllInBatch();
		bojLanguageRepository.deleteAllInBatch();
		bojRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("백준 스케줄링 정상 작동 테스트")
	public void bojUpdateTest() {
		//given
		User user1 = createUser("user1", "test");
		User user2 = createUser("user2", "test2");
		userRepository.saveAll(Arrays.asList(user1, user2));

		Language language1 = createLanguage("Java 11");
		Language language2 = createLanguage("C++98");
		Language language3 = createLanguage("Pascal");
		languageRepository.saveAll(Arrays.asList(language1, language2, language3));
		//when
		crawlingScheduler.bojUpdate();
		List<Baekjoon> response = bojRepository.findAll();

		//then
		assertThat(response).hasSize(2);
	}

	@Test
	@DisplayName("스케줄링 백준 이상한 유저일 경우 테스트")
	public void bojUpdateBojNullTest() {
		//given
		User user1 = createUser("user1", "test");
		User user2 = createUser("user2", "t");
		userRepository.saveAll(Arrays.asList(user1, user2));

		Language language1 = createLanguage("Java 11");
		Language language2 = createLanguage("C++98");
		Language language3 = createLanguage("Pascal");
		languageRepository.saveAll(Arrays.asList(language1, language2, language3));
		//when
		crawlingScheduler.bojUpdate();
		List<Baekjoon> response = bojRepository.findAll();

		//then
		assertThat(response).hasSize(1);
	}

	@Test
	@DisplayName("백준 스케줄링 값 정확도 테스트")
	public void bojUpdateCheckTest() {
		//given
		User user1 = createUser("user1", "test");
		userRepository.saveAll(Arrays.asList(user1));

		Language language1 = createLanguage("Java 11");
		Language language2 = createLanguage("C++98");
		Language language3 = createLanguage("Pascal");
		languageRepository.saveAll(Arrays.asList(language1, language2, language3));
		//when
		crawlingScheduler.bojUpdate();
		List<Baekjoon> response = bojRepository.findAll();

		//then
		assertThat(response.get(0)).extracting(Baekjoon::getTier, Baekjoon::getPassCount, Baekjoon::getTryFailCount,
			Baekjoon::getSubmitCount, Baekjoon::getFailCount).containsExactly(0, 0, 1, 2, 1);
	}

	@Test
	@DisplayName("백준 스케줄링 랭크 정확도 테스트")
	public void bojUpdateRankCheckTest() {
		//given
		User user1 = createUser("user1", "test");
		User user2 = createUser("user2", "test2");
		userRepository.saveAll(Arrays.asList(user1, user2));

		Baekjoon boj1 = createBaekjoon(user1, 15, 275, 9, 723, 73,
			100);
		Baekjoon boj2 = createBaekjoon(user2, 14, 275, 9, 723, 73,
			100, 1);
		bojRepository.saveAll(Arrays.asList(boj1, boj2));

		Language language1 = createLanguage("Java 11");
		Language language2 = createLanguage("C++98");
		Language language3 = createLanguage("Pascal");
		languageRepository.saveAll(Arrays.asList(language1, language2, language3));
		//when
		crawlingScheduler.bojUpdate();
		List<Baekjoon> response = bojRepository.findAll();

		//then
		assertThat(response.get(0)).extracting(Baekjoon::getPreviousRank).isEqualTo(1L);
		assertThat(response.get(1)).extracting(Baekjoon::getPreviousRank).isEqualTo(2L);
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

	private Baekjoon createBaekjoon(User user, int tier, int passCount, int tryFailCount, int submitCount,
		int failCount, int score, int previousRank) {
		return Baekjoon.builder()
			.user(user)
			.tier(tier)
			.passCount(passCount)
			.tryFailCount(tryFailCount)
			.submitCount(submitCount)
			.failCount(failCount)
			.score(score)
			.previousRank(previousRank)
			.build();
	}

	private Language createLanguage(String name) {
		return Language.builder()
			.name(name)
			.type(LanguageType.BAEKJOON)
			.build();
	}
}
