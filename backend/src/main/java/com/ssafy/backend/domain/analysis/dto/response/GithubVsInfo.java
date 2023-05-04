package com.ssafy.backend.domain.analysis.dto.response;

import java.util.List;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.backend.domain.analysis.dto.LanguageDetailResponse;
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
	double commit;
	double star;
	double repositories;
	LanguageDetailResponse languages;

	public static GithubVsInfo create(Github github, List<GithubDetailLanguage> languages) {
		return GithubVsInfo.builder()
			.nickname(github.getUser().getNickname())
			.avatarUrl(github.getUser().getImage())
			.commit(github.getCommitTotalCount())
			.star(github.getStarTotalCount())
			.repositories(github.countRepos())
			.languages(LanguageDetailResponse.create(languages))
			.build();
	}

	@QueryProjection
	public GithubVsInfo(double commit, double star) {
		this.commit = commit;
		this.star = star;
	}

	public void updateLanguages(List<GithubDetailLanguage> languages) {
		this.languages = LanguageDetailResponse.create(languages);
	}

	public void updateRepositories(double repositories) {
		this.repositories = repositories;
	}

	public void toOneDecimal() {
		this.commit = Math.round(this.commit * 10) / 10.0;
		this.star = Math.round(this.star * 10) / 10.0;
		this.repositories = Math.round(this.repositories * 10) / 10.0;
		this.languages.getLanguageList().forEach(GithubDetailLanguage::toOneDecimalPercentage);
	}

}
