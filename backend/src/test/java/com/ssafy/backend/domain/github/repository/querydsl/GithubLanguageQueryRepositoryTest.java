package com.ssafy.backend.domain.github.repository.querydsl;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
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
import com.ssafy.backend.domain.github.dto.FilteredGithubIdSet;
import com.ssafy.backend.domain.github.dto.GithubDetailLanguage;
import com.ssafy.backend.domain.github.repository.GithubLanguageRepository;
import com.ssafy.backend.domain.github.repository.GithubRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.util.repository.LanguageRepository;

@SpringBootTest
class GithubLanguageQueryRepositoryTest {
	@Autowired
	private LanguageRepository languageRepository;
	@Autowired
	private GithubLanguageRepository githubLanguageRepository;
	@Autowired
	private GithubRepository githubRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private GithubLanguageQueryRepository githubLanguageQueryRepository;

	@AfterEach
	public void teardown() {
		languageRepository.deleteAllInBatch();
		githubLanguageRepository.deleteAllInBatch();
		githubRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();
	}

	@DisplayName("공고에 지원한 유저의 언어별 평균을 얻을 수 있다. ")
	@Test
	void findAvgGroupByLanguage() {
		//given
		//유저 데이터
		User user1 = createUser("user1");
		User user2 = createUser("user2");
		User user3 = createUser("user3");
		User user4 = createUser("user4");
		userRepository.saveAll(Arrays.asList(user1, user2, user3, user4));

		//깃허브 데이터
		Github github1 = createGithub(user1, 100, 3, 800);
		Github github2 = createGithub(user2, 500, 2, 500);
		Github github3 = createGithub(user3, 1000, 2, 500);
		Github github4 = createGithub(user4, 1500, 2, 500);
		githubRepository.saveAll(Arrays.asList(github1, github2, github3, github4));

		long githubId1 = githubRepository.findByUser(user1).get().getId();
		long githubId2 = githubRepository.findByUser(user2).get().getId();
		long githubId3 = githubRepository.findByUser(user3).get().getId();
		long githubId4 = githubRepository.findByUser(user4).get().getId();

		// 언어 종류
		Language language1 = Language.builder().name("Java").type(LanguageType.GITHUB).build();
		Language language2 = Language.builder().name("Python").type(LanguageType.GITHUB).build();
		Language language3 = Language.builder().name("C++").type(LanguageType.GITHUB).build();
		Language language4 = Language.builder().name("C").type(LanguageType.GITHUB).build();
		Language language5 = Language.builder().name("JavaScript").type(LanguageType.GITHUB).build();
		Language language6 = Language.builder().name("TypeScript").type(LanguageType.GITHUB).build();
		languageRepository.saveAll(Arrays.asList(language1, language2, language3, language4, language5, language6));

		long languageId1 = languageRepository.findByNameAndType("Java", LanguageType.GITHUB).get().getId();
		long languageId2 = languageRepository.findByNameAndType("Python", LanguageType.GITHUB).get().getId();
		long languageId3 = languageRepository.findByNameAndType("C++", LanguageType.GITHUB).get().getId();
		long languageId4 = languageRepository.findByNameAndType("C", LanguageType.GITHUB).get().getId();
		long languageId5 = languageRepository.findByNameAndType("JavaScript", LanguageType.GITHUB).get().getId();
		long languageId6 = languageRepository.findByNameAndType("TypeScript", LanguageType.GITHUB).get().getId();

		//깃허브별 언어 사용
		GithubLanguage githubLanguage1 = createGithubLanguage(languageId1, 70, github1);
		GithubLanguage githubLanguage2 = createGithubLanguage(languageId1, 40, github2);
		GithubLanguage githubLanguage3 = createGithubLanguage(languageId2, 60, github2);
		GithubLanguage githubLanguage4 = createGithubLanguage(languageId3, 50, github3);
		GithubLanguage githubLanguage5 = createGithubLanguage(languageId4, 40, github3);
		GithubLanguage githubLanguage6 = createGithubLanguage(languageId5, 30, github3);
		GithubLanguage githubLanguage7 = createGithubLanguage(languageId6, 20, github3);
		GithubLanguage githubLanguage8 = createGithubLanguage(languageId1, 50, github4);
		githubLanguageRepository.saveAll(
			Arrays.asList(githubLanguage1, githubLanguage2, githubLanguage3, githubLanguage4, githubLanguage5,
				githubLanguage6, githubLanguage7, githubLanguage8));

		Set<Long> githubIdSet = new HashSet<>(Arrays.asList(githubId1, githubId2, githubId3, githubId4));
		FilteredGithubIdSet filteredGithubIdSet = FilteredGithubIdSet.create(githubIdSet);

		//when
		List<GithubDetailLanguage> result = githubLanguageQueryRepository.findAvgGroupByLanguage(filteredGithubIdSet);

		//then
		Assertions.assertThat(result)
			.hasSize(5)
			.extracting("name", "percentage")
			.containsExactly(tuple("Java", 160.0), tuple("Python", 60.0), tuple("C++", 50.0), tuple("C", 40.0),
				tuple("JavaScript", 30.0));
	}

	private User createUser(String nickname) {
		return User.builder().nickname(nickname).image("1").isDeleted(false).build();
	}

	private Github createGithub(User user, int commitTotalCount, int starTotalCount, int score) {
		return Github.builder()
			.user(user)
			.commitTotalCount(commitTotalCount)
			.followerTotalCount(10)
			.starTotalCount(starTotalCount)
			.score(score)
			.profileLink("1")
			.previousRank(1)
			.build();
	}

	private GithubLanguage createGithubLanguage(long languageId, double percentage, Github github) {
		return GithubLanguage.builder().languageId(languageId).github(github).percentage(percentage).build();
	}

}