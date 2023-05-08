package com.ssafy.backend.domain.analysis.dto.response;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ssafy.backend.domain.analysis.dto.LanguageInfo;
import com.ssafy.backend.domain.github.dto.GithubDetailLanguage;

class GithubVsInfoTest {
	@DisplayName("한자리수까지 자르기")
	@Test
	void test() {
		//given
		GithubDetailLanguage githubDetailLanguage1 = new GithubDetailLanguage("Java", 10.40304);
		GithubDetailLanguage githubDetailLanguage2 = new GithubDetailLanguage("Java", 10.123);
		GithubDetailLanguage githubDetailLanguage3 = new GithubDetailLanguage("Java", 11.0);
		List<GithubDetailLanguage> languages = new ArrayList<>(
			Arrays.asList(githubDetailLanguage1, githubDetailLanguage2, githubDetailLanguage3));

		GithubVsInfo githubVsInfo = GithubVsInfo.builder()
			.commit(1.11)
			.star(1.23)
			.repositories(3.0)
			.languages(LanguageInfo.create(languages))
			.build();

		//when
		githubVsInfo.toOneDecimal();

		//then
		assertThat(githubVsInfo).extracting("commit", "star", "repositories").containsExactly(1.1, 1.2, 3.0);
		Assertions.assertThat(githubVsInfo.getLanguages().getLanguageList())
			.extracting("percentage")
			.containsExactly(10.4, 10.12, 11.0);

	}

}