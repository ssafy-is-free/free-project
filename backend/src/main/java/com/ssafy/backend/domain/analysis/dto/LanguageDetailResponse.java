package com.ssafy.backend.domain.analysis.dto;

import java.util.List;

import com.ssafy.backend.domain.github.dto.GithubDetailLanguage;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LanguageDetailResponse {
	List<GithubDetailLanguage> languageList;

	public static LanguageDetailResponse create(List<GithubDetailLanguage> languages) {
		return LanguageDetailResponse.builder().languageList(languages).build();
	}

}
