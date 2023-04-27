package com.ssafy.backend.domain.github.dto;

import com.ssafy.backend.domain.entity.GithubRepo;

import lombok.Builder;

@Builder
public class GithubDetailRepo {
	long id;
	String name;
	String link;

	public static GithubDetailRepo create(GithubRepo githubRepo) {
		return GithubDetailRepo.builder().id(githubRepo.getId())
			.name(githubRepo.getName())
			.link(githubRepo.getRepositoryLink())
			.build();

	}
}
