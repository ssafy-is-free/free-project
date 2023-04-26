package com.ssafy.backend.domain.github.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.GithubRepo;

import lombok.Getter;

@Getter
public class GithubDetailResponse {
	Long githubId;
	String nickname;
	String profileLink;
	String avatarUrl;
	Integer commit;
	Integer star;
	Integer followers;
	List<Repo> repositories;
	List<GithubDetailLanguage> languages;

	@Getter
	public static class Repo {
		long id;
		String name;
		String link;

		public Repo(GithubRepo githubRepo) {
			this.id = githubRepo.getId();
			this.name = githubRepo.getName();
			this.link = githubRepo.getRepositoryLink();
		}
	}

	public GithubDetailResponse(Github github, List<GithubDetailLanguage> languages) {
		this.githubId = github.getId();
		this.nickname = github.getUser().getNickname();
		this.profileLink = github.getProfileLink();
		this.avatarUrl = github.getUser().getImage();
		this.commit = github.getCommitTotalCount();
		this.star = github.getStarTotalCount();
		this.followers = github.getFollowerTotalCount();
		this.repositories = github.getGithubRepos().stream().map(Repo::new).collect(Collectors.toList());
		this.languages = languages;
	}

}
