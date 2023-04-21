package com.ssafy.backend.domain.github.dto;

import com.ssafy.backend.domain.entity.Github;

public class GithubRankingCover {
	Long userId;
	String nickname;
	Integer rank;
	Integer score;
	String avatarUrl;
	Integer rankUpDown;

	public GithubRankingCover(Github github) {
		this.userId = github.getUser().getId();
		this.nickname = github.getUser().getNickname();
		this.score = github.getScore();
		this.avatarUrl = github.getUser().getImage();
	}
}
