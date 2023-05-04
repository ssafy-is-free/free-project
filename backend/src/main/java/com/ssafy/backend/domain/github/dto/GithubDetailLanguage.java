package com.ssafy.backend.domain.github.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class GithubDetailLanguage {
	String name;
	double percentage;

	@QueryProjection
	public GithubDetailLanguage(String name, double percentage) {
		this.name = name;
		this.percentage = percentage;
	}

	public void toOneDecimalPercentage() {
		this.percentage = Math.round(this.percentage * 10) / 10.0;

	}

	public void dividePercentage(long num) {
		this.percentage = this.percentage / num;
	}

}
