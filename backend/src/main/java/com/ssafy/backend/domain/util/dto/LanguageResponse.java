package com.ssafy.backend.domain.util.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.backend.domain.entity.Language;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LanguageResponse {

	private long languageId;

	private String name;

	public static LanguageResponse create(Language language) {

		return LanguageResponse.builder()
			.languageId(language.getId())
			.name(language.getName()).build();

	}

	public static List<LanguageResponse> createList(List<Language> languages) {

		return languages.stream()
			.map(LanguageResponse::create)
			.collect(Collectors.toList());

	}

}
