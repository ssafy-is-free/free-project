package com.ssafy.backend.domain.github.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class GithubDetailLanguage {
	String name;
	String percentage;

	@QueryProjection
	public GithubDetailLanguage(String name, String percentage) {
		this.name = name;
		this.percentage = percentage;
	}

}
