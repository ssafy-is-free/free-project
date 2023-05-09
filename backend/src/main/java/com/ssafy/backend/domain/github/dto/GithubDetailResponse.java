package com.ssafy.backend.domain.github.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.GithubRepo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GithubDetailResponse {
	Long githubId;
	String nickname;
	String profileLink;
	String avatarUrl;
	Integer commit;
	Integer star;
	Integer followers;
	List<GithubDetailRepo> repositories;
	List<GithubDetailLanguage> languages;
	boolean isMine;

	public static GithubDetailResponse create(Github github, boolean isMine, List<GithubDetailLanguage> languages) {
		return GithubDetailResponse.builder()
			.githubId(github.getId())
			.nickname(github.getUser().getNickname())
			.profileLink(github.getProfileLink())
			.avatarUrl(github.getUser().getImage())
			.commit(github.getCommitTotalCount())
			.star(github.getStarTotalCount())
			.followers(github.getFollowerTotalCount())
			.repositories(isPublicRepo(github) ? toDetailRepoDto(github.getGithubRepos()) : null)
			.languages(languages)
			.isMine(isMine)
			.build();
	}

	private static boolean isPublicRepo(Github github) {
		return github.isPublic();
	}

	private static List<GithubDetailRepo> toDetailRepoDto(Set<GithubRepo> githubRepoSet) {
		return githubRepoSet.stream().map(GithubDetailRepo::create).collect(Collectors.toList());

	}

}
