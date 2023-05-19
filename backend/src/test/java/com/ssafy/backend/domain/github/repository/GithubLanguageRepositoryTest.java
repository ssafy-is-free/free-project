package com.ssafy.backend.domain.github.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.ssafy.backend.TestConfig;
import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.GithubLanguage;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.github.repository.projection.GithubOnly;
import com.ssafy.backend.domain.user.repository.UserRepository;

@DataJpaTest
@Transactional
@Import(TestConfig.class)
class GithubLanguageRepositoryTest {
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private GithubLanguageRepository githubLanguageRepository;
	@Autowired
	private GithubRepository githubRepository;
	@Autowired
	private UserRepository userRepository;

	@After
	public void teardown() {
		userRepository.deleteAllInBatch();
		githubRepository.deleteAllInBatch();
		githubLanguageRepository.deleteAllInBatch();
		this.entityManager.createNativeQuery(
				"ALTER TABLE users ALTER COLUMN `id` RESTART WITH 1")
			.executeUpdate();
	}

	@Test
	@DisplayName("해당 언어를 사용하는 깃허브가 없으면 빈배열을 반환한다.")
	void findByLanguageId_NoMatch() {
		//given
		//유저 데이터
		User user1 = createUser("user1");
		User user2 = createUser("user2");
		User user3 = createUser("user3");
		List<User> userList = new ArrayList<>(Arrays.asList(user1, user2, user3));
		userRepository.saveAll(userList);

		//깃허브 데이터
		Github github1 = createGithub(user1);
		Github github2 = createGithub(user2);
		Github github3 = createGithub(user3);
		List<Github> githubList = new ArrayList<>(Arrays.asList(github1, github2, github3));
		githubRepository.saveAll(githubList);

		//깃허브 언어 데이터
		GithubLanguage githubLanguage1 = createGithubLanguage(1L, github1);
		GithubLanguage githubLanguage2 = createGithubLanguage(1L, github2);
		GithubLanguage githubLanguage3 = createGithubLanguage(3L, github3);

		List<GithubLanguage> githubLanguageList = new ArrayList<>(
			Arrays.asList(githubLanguage1, githubLanguage2, githubLanguage3));
		githubLanguageRepository.saveAll(githubLanguageList);

		long languageId = 2L;

		//when
		List<GithubOnly> result = githubLanguageRepository.findByLanguageId(languageId);

		//then
		assertThat(result).hasSize(0).isEmpty();
	}

	@Test
	@DisplayName("해당 언어를 사용하는 깃허브의 아이디를 리스트형태로 반환한다.")
	void findByLanguageId() {
		//given
		//유저 데이터
		User user1 = createUser("user1");
		User user2 = createUser("user2");
		User user3 = createUser("user3");
		List<User> userList = new ArrayList<>(Arrays.asList(user1, user2, user3));
		userRepository.saveAll(userList);

		//깃허브 데이터
		Github github1 = createGithub(user1);
		Github github2 = createGithub(user2);
		Github github3 = createGithub(user3);
		List<Github> githubList = new ArrayList<>(Arrays.asList(github1, github2, github3));
		githubRepository.saveAll(githubList);

		//깃허브 언어 데이터
		GithubLanguage githubLanguage1 = createGithubLanguage(1L, github1);
		GithubLanguage githubLanguage2 = createGithubLanguage(1L, github2);
		GithubLanguage githubLanguage3 = createGithubLanguage(3L, github3);

		List<GithubLanguage> githubLanguageList = new ArrayList<>(
			Arrays.asList(githubLanguage1, githubLanguage2, githubLanguage3));
		githubLanguageRepository.saveAll(githubLanguageList);

		long languageId = 1L;

		//when
		List<GithubOnly> result = githubLanguageRepository.findByLanguageId(languageId);

		//then
		assertThat(result).hasSize(2).extracting(githubOnly -> githubOnly.getGithub().getId()).containsExactly(1L, 2L);
	}

	private User createUser(String nickname) {
		return User.builder().nickname(nickname).image("1").isDeleted(false).build();
	}

	private Github createGithub(User user) {
		return Github.builder()
			.user(user)
			.commitTotalCount(1)
			.followerTotalCount(1)
			.starTotalCount(1)
			.score(1)
			.profileLink("1")
			.previousRank(1)
			.build();
	}

	private GithubLanguage createGithubLanguage(long languageId, Github github) {
		return GithubLanguage.builder().languageId(languageId).github(github).percentage(40).build();
	}
}