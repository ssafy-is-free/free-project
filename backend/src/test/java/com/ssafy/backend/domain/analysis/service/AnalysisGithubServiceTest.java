package com.ssafy.backend.domain.analysis.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.backend.domain.analysis.dto.response.CompareGithubResponse;
import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.GithubLanguage;
import com.ssafy.backend.domain.entity.GithubRepo;
import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.entity.common.LanguageType;
import com.ssafy.backend.domain.github.repository.GithubLanguageRepository;
import com.ssafy.backend.domain.github.repository.GithubRepoRepository;
import com.ssafy.backend.domain.github.repository.GithubRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.util.repository.LanguageRepository;

@SpringBootTest
class AnalysisGithubServiceTest {

	@Autowired
	private AnalysisGithubService analysisGithubService;

	@Autowired
	private LanguageRepository languageRepository;
	@Autowired
	private GithubLanguageRepository githubLanguageRepository;
	@Autowired
	private GithubRepoRepository githubRepoRepository;
	@Autowired
	private GithubRepository githubRepository;
	@Autowired
	private UserRepository userRepository;

	@AfterEach
	void tearDown() {
		languageRepository.deleteAllInBatch();
		githubLanguageRepository.deleteAllInBatch();
		githubRepoRepository.deleteAllInBatch();
		githubRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();

	}

	@DisplayName("나와 다른 유저의 깃허브 정보를 비교할 수 있다.")
	@Test
	void compareWithOpponent() {
		//given
		//유저 데이터
		User user1 = createUser("user1");
		User user2 = createUser("user2");
		userRepository.saveAll(Arrays.asList(user1, user2));

		//깃허브 데이터
		Github myGithub = createGithub(user1, 1000, 10, 3, 800);
		Github yourGithub = createGithub(user2, 500, 4, 2, 500);
		githubRepository.saveAll(Arrays.asList(myGithub, yourGithub));

		// //깃허브 언어 데이터

		// GithubLanguage githubLanguage1 = createGithubLanguage(1L, 30, myGithub);
		// GithubLanguage githubLanguage2 = createGithubLanguage(1L, 70, myGithub);
		// GithubLanguage githubLanguage3 = createGithubLanguage(3L, 100, yourGithub);
		// githubLanguageRepository.saveAll(Arrays.asList(githubLanguage1, githubLanguage2, githubLanguage3));

		//깃허브 레포 데이터
		GithubRepo repo1 = createGithubRepo("repository1", myGithub);
		GithubRepo repo2 = createGithubRepo("repository2", myGithub);
		GithubRepo repo3 = createGithubRepo("repository3", yourGithub);
		githubRepoRepository.saveAll(Arrays.asList(repo1, repo2, repo3));

		long myUserId = userRepository.findByNickname("user1").orElse(user1).getId();
		long opponentUserId = userRepository.findByNickname("user2").orElse(user2).getId();

		//when
		CompareGithubResponse rseult = analysisGithubService.compareWithOpponent(opponentUserId, myUserId);

		//then
		//language와 repository를 제외한 데이터를 제대로 가지고 오는지
		assertThat(rseult.getMy()).extracting("nickname", "avatarUrl", "commit", "star")
			.contains("user1", "1", 1000, 3);

		assertThat(rseult.getOpponent()).extracting("nickname", "avatarUrl", "commit", "star")
			.contains("user2", "1", 500, 2);

		//repository의 수를 제대로 가지고 오는지
		assertThat(rseult.getMy().getRepositories()).isEqualTo(2);
		assertThat(rseult.getOpponent().getRepositories()).isEqualTo(1);

	}

	@DisplayName("유저 비교에 사용된 언어 정보는 퍼센트 순으로 정렬되어있다")
	@Test
	void compareWithOpponentLanguage() {
		//given
		//유저 데이터
		User user1 = createUser("user1");
		User user2 = createUser("user2");
		userRepository.saveAll(Arrays.asList(user1, user2));

		//깃허브 데이터
		Github myGithub = createGithub(user1, 1000, 10, 3, 800);
		Github yourGithub = createGithub(user2, 500, 4, 2, 500);
		githubRepository.saveAll(Arrays.asList(myGithub, yourGithub));

		// //깃허브 언어 데이터
		Language language1 = Language.builder().name("Java").type(LanguageType.GITHUB).build();
		Language language2 = Language.builder().name("Python").type(LanguageType.GITHUB).build();
		languageRepository.saveAll(Arrays.asList(language1, language2));

		long languageId1 = languageRepository.findByNameAndType("Java", LanguageType.GITHUB).get().getId();
		long languageId2 = languageRepository.findByNameAndType("Python", LanguageType.GITHUB).get().getId();

		GithubLanguage githubLanguage2 = createGithubLanguage(languageId1, "70", myGithub);
		GithubLanguage githubLanguage1 = createGithubLanguage(languageId2, "30", myGithub);
		GithubLanguage githubLanguage3 = createGithubLanguage(languageId2, "100", yourGithub);
		githubLanguageRepository.saveAll(Arrays.asList(githubLanguage1, githubLanguage2, githubLanguage3));

		long myUserId = userRepository.findByNickname("user1").orElse(user1).getId();
		long opponentUserId = userRepository.findByNickname("user2").orElse(user2).getId();

		//when
		CompareGithubResponse result = analysisGithubService.compareWithOpponent(opponentUserId, myUserId);

		//then
		assertThat(result.getMy().getLanguages()).hasSize(2)
			.extracting("name", "percentage")
			.containsExactly(tuple("Java", "70"), tuple("Python", "30"));
		assertThat(result.getOpponent().getLanguages()).hasSize(1)
			.extracting("name", "percentage")
			.containsExactly(tuple("Python", "100"));

	}

	private User createUser(String nickname) {
		return User.builder().nickname(nickname).image("1").isDeleted(false).build();
	}

	private Github createGithub(User user, int commitTotalCount, int followerTotalCount, int starTotalCount,
		int score) {
		return Github.builder()
			.user(user)
			.commitTotalCount(commitTotalCount)
			.followerTotalCount(followerTotalCount)
			.starTotalCount(starTotalCount)
			.score(score)
			.profileLink("1")
			.previousRank(1)
			.build();
	}

	private GithubLanguage createGithubLanguage(long languageId, String percentage, Github github) {
		return GithubLanguage.builder().languageId(languageId).github(github).percentage(percentage).build();
	}

	public GithubRepo createGithubRepo(String name, Github github) {
		return GithubRepo.builder().name(name).readme("hi").repositoryLink("hi").github(github).build();
	}

}