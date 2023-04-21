package com.ssafy.backend.domain.github.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class LanguageCond {
	private List<Long> languages;

	public LanguageCond(List<Long> languages) {
		this.languages = languages;
	}
}
