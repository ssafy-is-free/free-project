package com.ssafy.backend.domain.github.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.GithubLanguage;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.github.repository.projection.GithubOnly;
import com.ssafy.backend.domain.user.repository.UserRepository;

@DataJpaTest
class GithubLanguageRepositoryTest {
	@Autowired
	private GithubLanguageRepository githubLanguageRepository;
	@Autowired
	private GithubRepository githubRepository;
	@Autowired
	private UserRepository userRepository;

	@Test
	@DisplayName("해당 언어를 사용하는 깃허브의 아이디조회")
	void findByLanguageId() {
		//given
		//유저 데이터
		User user1 = User.builder().id(1L).nickname("user1").image("1").isDeleted(false).build();
		User user2 = User.builder().id(2L).nickname("user2").image("1").isDeleted(false).build();
		User user3 = User.builder().id(3L).nickname("user3").image("1").isDeleted(false).build();
		List<User> userList = new ArrayList<>(Arrays.asList(user1, user2, user3));
		userRepository.saveAll(userList);

		//깃허브 데이터
		Github github1 = Github.builder()
			.id(1L)
			.user(user1)
			.commitTotalCount(1)
			.followerTotalCount(1)
			.starTotalCount(1)
			.score(1)
			.profileLink("1")
			.previousRank(1)
			.build();
		Github github2 = Github.builder()
			.id(2L)
			.user(user2)
			.commitTotalCount(1)
			.followerTotalCount(1)
			.starTotalCount(1)
			.score(1)
			.profileLink("1")
			.previousRank(1)
			.build();
		Github github3 = Github.builder()
			.id(3L)
			.user(user3)
			.commitTotalCount(1)
			.followerTotalCount(1)
			.starTotalCount(1)
			.score(1)
			.profileLink("1")
			.previousRank(1)
			.build();
		List<Github> githubList = new ArrayList<>(Arrays.asList(github1, github2, github3));
		githubRepository.saveAll(githubList);

		//깃허브 언어 데이터
		GithubLanguage githubLanguage1 = GithubLanguage.builder()
			.id(1L)
			.languageId(1L)
			.github(github1)
			.percentage("40")
			.build();
		GithubLanguage githubLanguage2 = GithubLanguage.builder()
			.id(2L)
			.languageId(1L)
			.github(github2)
			.percentage("40")
			.build();
		GithubLanguage githubLanguage3 = GithubLanguage.builder()
			.id(3L)
			.languageId(2L)
			.github(github3)
			.percentage("40")
			.build();
		List<GithubLanguage> githubLanguageList = new ArrayList<>(
			Arrays.asList(githubLanguage1, githubLanguage2, githubLanguage3));
		githubLanguageRepository.saveAll(githubLanguageList);

		long languageId = 1;

		//when
		List<GithubOnly> result = githubLanguageRepository.findByLanguageId(languageId);

		//then
		assertEquals(result.size(), 2);

	}
}