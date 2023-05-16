package com.ssafy.backend.domain.github.service;

import static com.ssafy.backend.global.response.exception.CustomExceptionStatus.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.GithubRepo;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.github.repository.GithubLanguageRepository;
import com.ssafy.backend.domain.github.repository.GithubRepoRepository;
import com.ssafy.backend.domain.github.repository.GithubRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.util.repository.LanguageRepository;
import com.ssafy.backend.global.response.exception.CustomException;

@SpringBootTest
public class GithubCrawlingServiceTest {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LanguageRepository languageRepository;
	@Autowired
	private GithubRepository githubRepository;
	@Autowired
	private GithubLanguageRepository githubLanguageRepository;
	@Autowired
	private GithubRepoRepository githubRepoRepository;
	@Autowired
	private GithubCrawlingService githubCrawlingService;

	@AfterEach
	void tearDown() {
		languageRepository.deleteAllInBatch();
		githubLanguageRepository.deleteAllInBatch();
		githubRepoRepository.deleteAllInBatch();
		githubRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("깃허브 닉네임 업데이트 크롤링 정상 작동 테스ㅌ")
	public void updateAllGithubTest() {
		//given
		User user = createUser("test");
		userRepository.save(user);
		Github github = createGithub(user, true);
		githubRepository.save(github);

		//when
		githubCrawlingService.updateAllGithub("test", user.getId());
		List<GithubRepo> githubRepos = githubRepoRepository.findByGithub(github);

		//then
		assertThat(githubRepos).hasSize(4);

	}

	@Test
	@DisplayName("깃허브 닉네임 업데이트 없는 닉네임 테스ㅌ")
	public void updateAllGithubNicknameNullTest() {
		//given
		User user = createUser("!");
		userRepository.save(user);
		Github github = createGithub(user, true);
		githubRepository.save(github);

		//when	//then
		assertThatThrownBy(() -> githubCrawlingService.updateAllGithub("!", user.getId())).isInstanceOf(
			CustomException.class).hasFieldOrPropertyWithValue("customExceptionStatus", NOT_FOUND_GITHUB);

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
}
