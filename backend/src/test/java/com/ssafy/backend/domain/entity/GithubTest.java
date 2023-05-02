package com.ssafy.backend.domain.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GithubTest {

	@DisplayName("깃허브가 가진 리포지토리의 수를 반환한다.")
	@Test
	void countRepos() {
		//given
		User user = createUser("user1");
		Github github = createGithub(user);

		GithubRepo repo1 = createGithubRepo("repository1", github);
		GithubRepo repo2 = createGithubRepo("repository2", github);
		GithubRepo repo3 = createGithubRepo("repository3", github);

		Set<GithubRepo> repoSet = new HashSet<>();
		repoSet.addAll(Arrays.asList(repo1, repo2, repo3));
		github.updateGithubRepos(repoSet);

		//when
		long count = github.countRepos();
		//then
		assertThat(count).isEqualTo(3);
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

	public GithubRepo createGithubRepo(String name, Github github) {
		return GithubRepo.builder().name(name).readme("hi").repositoryLink("hi").github(github).build();
	}
}