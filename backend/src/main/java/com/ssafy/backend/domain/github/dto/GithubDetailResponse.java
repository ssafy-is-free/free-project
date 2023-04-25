package com.ssafy.backend.domain.github.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.GithubLanguage;
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
	List<Repositories> repositories;
	List<Languages> languages;

	@Getter
	static class Repositories {
		long id;
		String name;
		String link;

		public Repositories(GithubRepo githubRepo) {
			this.id = githubRepo.getId();
			this.name = githubRepo.getName();
			this.link = githubRepo.getRepositoryLink();
		}
	}

	@Getter
	static class Languages {
		String name;
		String percentage;

		public Languages(GithubLanguage githubLanguage) {
			this.name = String.valueOf(githubLanguage.getLanguageId());
			this.percentage = githubLanguage.getPercentage();
		}
	}

	public GithubDetailResponse(Github github) {
		this.githubId = github.getId();
		this.nickname = github.getUser().getNickname();
		this.profileLink = github.getProfileLink();
		this.avatarUrl = github.getUser().getImage();
		this.commit = github.getCommitTotalCount();
		this.star = github.getStarTotalCount();
		this.followers = github.getFollowerTotalCount();
		this.repositories = github.getGithubRepos().stream().map(Repositories::new).collect(Collectors.toList());
		this.languages = github.getGithubLanguages().stream().map(Languages::new).collect(Collectors.toList());
	}

}
