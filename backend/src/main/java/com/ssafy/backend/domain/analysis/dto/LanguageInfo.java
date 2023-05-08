package com.ssafy.backend.domain.analysis.dto;

import java.util.List;

import com.ssafy.backend.domain.github.dto.GithubDetailLanguage;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LanguageInfo {
	List<GithubDetailLanguage> languageList;

	public static LanguageInfo create(List<GithubDetailLanguage> languages) {
		return LanguageInfo.builder().languageList(languages).build();
	}

}
