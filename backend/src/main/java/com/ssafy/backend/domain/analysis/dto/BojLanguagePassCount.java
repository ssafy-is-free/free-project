package com.ssafy.backend.domain.analysis.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class BojLanguagePassCount {
	private Long languageId;
	private Integer totalPassCount;

	@QueryProjection
	public BojLanguagePassCount(Long languageId, Integer totalPassCount) {
		this.languageId = languageId;
		this.totalPassCount = totalPassCount;
	}

}
