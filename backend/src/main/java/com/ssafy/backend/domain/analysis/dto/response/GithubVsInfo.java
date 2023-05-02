package com.ssafy.backend.domain.analysis.dto.response;

import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.github.dto.GithubDetailLanguage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GithubVsInfo {
	String nickname;
	String avatarUrl;
	int commit;
	int star;
	long repositories;
	GithubDetailLanguage languages;

	public static GithubVsInfo crate(Github github) {
		return GithubVsInfo.builder()
			.nickname(github.getUser().getNickname())
			.avatarUrl(github.getUser().getImage())
			.commit(github.getCommitTotalCount())
			.star(github.getStarTotalCount())
			.repositories(github.countRepos())
			.build();
	}

}
