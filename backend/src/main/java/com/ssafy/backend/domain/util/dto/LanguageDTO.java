package com.ssafy.backend.domain.util.dto;

import com.ssafy.backend.domain.entity.Language;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LanguageDTO {

	private long languageId;

	private String name;

	public static LanguageDTO createDTO(Language language) {

		return LanguageDTO.builder()
			.languageId(language.getId())
			.name(language.getName()).build();

	}
}
