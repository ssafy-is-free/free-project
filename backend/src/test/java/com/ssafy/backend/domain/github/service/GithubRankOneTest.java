package com.ssafy.backend.domain.github.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.GithubLanguage;
import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.entity.common.LanguageType;
import com.ssafy.backend.domain.github.dto.GitHubRankingFilter;
import com.ssafy.backend.domain.github.dto.GithubRankingOneResponse;
import com.ssafy.backend.domain.github.repository.GithubLanguageRepository;
import com.ssafy.backend.domain.github.repository.GithubRepository;
import com.ssafy.backend.domain.job.repository.JobHistoryRepository;
import com.ssafy.backend.domain.job.repository.JobPostingRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.util.repository.LanguageRepository;

@SpringBootTest
class GithubRankOneTest {

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
	@Autowired
	private LanguageRepository languageRepository;
	@Autowired
	private GithubLanguageRepository githubLanguageRepository;

	@AfterEach
	void tearDown() {
		githubLanguageRepository.deleteAllInBatch();
		languageRepository.deleteAllInBatch();
		jobHistoryRepository.deleteAllInBatch();
		jobPostingRepository.deleteAllInBatch();
		githubRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();

	}

	@DisplayName("해당 유저의 깃허브 커버 정보를 조회할 수 있다.")
	@Test
	void getGithubRankOne() {
		//given
		User user1 = createUser("user1");
		User user2 = createUser("user2");
		User user3 = createUser("user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Github github1 = createGithub(user1, 100, 1);
		Github github2 = createGithub(user2, 200, 2);
		Github github3 = createGithub(user3, 200, 3);
		githubRepository.saveAll(Arrays.asList(github1, github2, github3));

		Language java = createLanguage("JAVA");
		languageRepository.save(java);

		long languageId = languageRepository.findByNameAndType("JAVA", LanguageType.GITHUB).get().getId();

		GithubLanguage githubLanguage1 = createGithubLanguage(languageId, github1);
		GithubLanguage githubLanguage2 = createGithubLanguage(languageId, github2);
		githubLanguageRepository.saveAll(Arrays.asList(githubLanguage1, githubLanguage2));

		GitHubRankingFilter rankingFilter = GitHubRankingFilter.builder().build();
		long userId = userRepository.findByNickname("user1").getId();
		//when
		GithubRankingOneResponse response = githubRankingService.getGithubRankOne(userId, rankingFilter);

		//then
		assertThat(response.getGithubRankingCover())
			.extracting("rank", "rankUpDown")
			.containsExactly(3L, -2L);

	}

	@DisplayName("해당 언어를 사용하는 유저의 깃허브 커버 정보를 조회할 수 있다.")
	@Test
	void getGithubRankOneByLanguage() {
		//given
		User user1 = createUser("user1");
		User user2 = createUser("user2");
		User user3 = createUser("user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Github github1 = createGithub(user1, 100, 3);
		Github github2 = createGithub(user2, 200, 2);
		Github github3 = createGithub(user3, 200, 1);
		githubRepository.saveAll(Arrays.asList(github1, github2, github3));

		Language java = createLanguage("JAVA");
		languageRepository.save(java);

		long languageId = languageRepository.findByNameAndType("JAVA", LanguageType.GITHUB).get().getId();

		GithubLanguage githubLanguage1 = createGithubLanguage(languageId, github1);
		GithubLanguage githubLanguage2 = createGithubLanguage(languageId, github2);
		githubLanguageRepository.saveAll(Arrays.asList(githubLanguage1, githubLanguage2));

		GitHubRankingFilter rankingFilter = GitHubRankingFilter.builder().languageId(languageId).build();
		long userId = userRepository.findByNickname("user1").getId();
		//when
		GithubRankingOneResponse response = githubRankingService.getGithubRankOne(userId, rankingFilter);

		//then
		assertThat(response.getGithubRankingCover())
			.extracting("rank", "rankUpDown")
			.containsExactly(2L, null);

	}

	@DisplayName("해당 유저가 해당 언어를 사용하지 않는다면 조회 결과가 없다")
	@Test
	void getGithubRankOneByNoUseLanguage() {
		//given
		User user1 = createUser("user1");
		User user2 = createUser("user2");
		User user3 = createUser("user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Github github1 = createGithub(user1, 100, 3);
		Github github2 = createGithub(user2, 200, 2);
		Github github3 = createGithub(user3, 200, 1);
		githubRepository.saveAll(Arrays.asList(github1, github2, github3));

		Language java = createLanguage("JAVA");
		languageRepository.save(java);

		long languageId = languageRepository.findByNameAndType("JAVA", LanguageType.GITHUB).get().getId();

		GitHubRankingFilter rankingFilter = GitHubRankingFilter.builder().languageId(languageId).build();
		long userId = userRepository.findByNickname("user1").getId();

		//when
		GithubRankingOneResponse response = githubRankingService.getGithubRankOne(userId, rankingFilter);

		//then
		assertThat(response.getGithubRankingCover()).isNull();

	}

	private User createUser(String nickname) {
		return User.builder().nickname(nickname).image("1").isDeleted(false).build();
	}

	private Github createGithub(User user, Integer score, long prevRank) {
		return Github.builder()
			.user(user)
			.commitTotalCount(1)
			.followerTotalCount(1)
			.starTotalCount(1)
			.score(score)
			.profileLink("1")
			.previousRank(prevRank)
			.build();
	}

	public Language createLanguage(String name) {
		return Language.builder().name(name).type(LanguageType.GITHUB).build();
	}

	public GithubLanguage createGithubLanguage(long languageId, Github github) {
		return GithubLanguage.builder().languageId(languageId).github(github).percentage(100).build();
	}

}