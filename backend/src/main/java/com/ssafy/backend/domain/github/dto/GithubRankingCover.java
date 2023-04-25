package com.ssafy.backend.domain.github.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.backend.domain.entity.Github;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GithubRankingCover {
	long userId;
	String nickname;
	long rank;
	Integer score;
	String avatarUrl;
	long rankUpDown;
	@JsonIgnore
	long prevRank;

	public GithubRankingCover(Github github) {
		this.userId = github.getUser().getId();
		this.nickname = github.getUser().getNickname();
		this.prevRank = github.getPreviousRank();
		this.score = github.getScore();
		this.avatarUrl = github.getUser().getImage();

	}
}
