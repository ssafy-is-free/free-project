package com.ssafy.backend.domain.github.service;

import java.util.Arrays;

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
import com.ssafy.backend.domain.github.dto.GithubDetailResponse;
import com.ssafy.backend.domain.github.repository.GithubLanguageRepository;
import com.ssafy.backend.domain.github.repository.GithubRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.util.repository.LanguageRepository;

@SpringBootTest
class GithubServiceTest {

	@Autowired
	private GithubService githubService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private GithubRepository githubRepository;
	@Autowired
	private GithubLanguageRepository githubLanguageRepository;
	@Autowired
	private LanguageRepository languageRepository;

	@AfterEach
	void tearDown() {
		githubLanguageRepository.deleteAllInBatch();
		languageRepository.deleteAllInBatch();
		githubRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();

	}

	// @Test
	// @DisplayName("해당 유저의 깃허브 상세 정보를 조회한다.")
	// void getDetails() {
	// 	//given
	// 	User user1 = createUser("user1"); //1L
	// 	User user2 = createUser("user2"); //2L
	// 	userRepository.saveAll(Arrays.asList(user1, user2));
	//
	// 	Github github1 = createGithub(user2, true); //1L
	// 	Github github2 = createGithub(user1, true); //2L
	// 	githubRepository.saveAll(Arrays.asList(github1, github2));
	//
	// 	long userId = userRepository.findByNickname("user1").getId();
	// 	//when
	// 	GithubDetailResponse results = githubService.getDetails(userId, false);
	//
	// 	//then
	// 	Assertions.assertThat(results.getGithubId()).isEqualTo(userId);
	// }
	//
	// @Test
	// @DisplayName("해당 유저의 깃허브에서 사용한 언어 정보를 조회한다.")
	// void getDetailsWithLanguage() {
	// 	//given
	// 	//유저
	// 	User user1 = createUser("user1"); //1L
	// 	userRepository.save(user1);
	// 	long userId = userRepository.findByNickname("user1").getId();
	//
	// 	//깃허브
	// 	Github github1 = createGithub(user1, true); //1L
	// 	githubRepository.save(github1);
	//
	// 	//깃허브 언어
	// 	Language language1 = createLanguage("Java"); //1L
	// 	Language language2 = createLanguage("C++"); //2L
	// 	languageRepository.saveAll(Arrays.asList(language1, language2));
	// 	long languageId1 = languageRepository.findByNameAndType("Java", LanguageType.GITHUB).get().getId();
	// 	long languageId2 = languageRepository.findByNameAndType("C++", LanguageType.GITHUB);
	//
	// 	GithubLanguage githubLanguage1 = createGithubLanguage(1L, github1);
	// 	GithubLanguage githubLanguage2 = createGithubLanguage(2L, github1);
	// 	githubLanguageRepository.saveAll(Arrays.asList(githubLanguage1, githubLanguage2));
	//
	// 	//when
	// 	GithubDetailResponse results = githubService.getDetails(userId, false);
	//
	// 	//then
	// 	Assertions.assertThat(results.getLanguages())
	// 		.hasSize(2)
	// 		.extracting(GithubDetailLanguage::getName)
	// 		.containsExactly("Java", "C++");
	// }

	@Test
	@DisplayName("공개 유저의 레포 정보는 공개된다.")
	void getDetailsWithPublic() {
		//given
		User user1 = createUser("user1"); //1L
		userRepository.saveAll(Arrays.asList(user1));

		Github github1 = createGithub(user1, true); //1L
		githubRepository.saveAll(Arrays.asList(github1));

		long userId = userRepository.findByNickname("user1").getId();

		//when
		GithubDetailResponse results = githubService.getDetails(userId, false);

		//then
		Assertions.assertThat(results.getRepositories()).isNotNull();
	}

	@Test
	@DisplayName("비공개 유저의 레포 정보는 공개되지 않는다.")
	void getDetailsWithPrivate() {
		//given
		User user1 = createUser("user1"); //1L
		userRepository.saveAll(Arrays.asList(user1));

		Github github1 = createGithub(user1, false); //1L
		githubRepository.saveAll(Arrays.asList(github1));

		long userId = userRepository.findByNickname("user1").getId();

		//when
		GithubDetailResponse results = githubService.getDetails(userId, false);

		//then
		Assertions.assertThat(results.getRepositories()).isNull();
	}

	private User createUser(String nickname) {
		return User.builder().nickname(nickname).image("1").isDeleted(false).build();
	}

	private Github createGithub(User user, boolean isPublic) {
		return Github.builder()
			.user(user)
			.commitTotalCount(1)
			.followerTotalCount(1)
			.starTotalCount(1)
			.score(1)
			.profileLink("1")
			.previousRank(1)
			.isPublic(isPublic)
			.build();
	}

	private GithubLanguage createGithubLanguage(long languageId, Github github) {
		return GithubLanguage.builder().languageId(languageId).github(github).percentage(40).build();
	}

	private Language createLanguage(String name) {
		return Language.builder().name(name).type(LanguageType.GITHUB).build();
	}
}