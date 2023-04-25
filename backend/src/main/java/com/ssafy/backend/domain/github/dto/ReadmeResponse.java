package com.ssafy.backend.domain.github.dto;

import com.ssafy.backend.domain.entity.GithubRepo;

import lombok.Getter;

@Getter
public class ReadmeResponse {
	String readme;

	public ReadmeResponse(GithubRepo githubRepo) {
		this.readme = githubRepo.getReadme();
	}

}
