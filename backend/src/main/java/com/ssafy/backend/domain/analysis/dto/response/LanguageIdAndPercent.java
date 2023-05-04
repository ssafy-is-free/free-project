package com.ssafy.backend.domain.analysis.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LanguageIdAndPercent {
	long languageId;
	double percent;

	@QueryProjection
	public LanguageIdAndPercent(long languageId, double percent) {
		this.languageId = languageId;
		this.percent = percent;
	}
}
