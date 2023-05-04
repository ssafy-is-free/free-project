package com.ssafy.backend.domain.github.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.backend.domain.entity.Github;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GithubRankingCover {
	long userId;
	String nickname;
	long rank;
	Integer score;
	String avatarUrl;
	Long rankUpDown;
	@JsonIgnore
	long prevRank;

	public static GithubRankingCover create(Github github) {
		return GithubRankingCover.builder()
			.userId(github.getUser().getId())
			.nickname(github.getUser().getNickname())
			.prevRank(github.getPreviousRank())
			.score(github.getScore())
			.avatarUrl(github.getUser().getImage())
			.build();

	}

	public void setRank(long rank) {
		this.rank = rank;
	}

	public void setRankUpDown(long rankUpDown) {
		this.rankUpDown = rankUpDown;
	}

	public long getRankUpDown(long rank) {
		return this.getPrevRank() != 0 ? this.getPrevRank() - rank : 0;
	}
}
