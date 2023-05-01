package com.ssafy.backend.domain.algorithm.dto.response;

import java.util.List;

import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BojInfoDetailResponse {
	private String bojId;
	private String tierUrl;
	private int pass;
	private int tryFail;
	private int submit;
	private int fail;
	private List<BojLanguageResponse> languages;

	public static BojInfoDetailResponse create(User user, Baekjoon baekjoon,
		List<BojLanguageResponse> languageDTOList) {
		return BojInfoDetailResponse.builder()
			.bojId(user.getBojId())
			.tierUrl(baekjoon.getTier())
			.pass(baekjoon.getPassCount())
			.tryFail(baekjoon.getTryFailCount())
			.submit(baekjoon.getSubmitCount())
			.fail(baekjoon.getFailCount())
			.languages(languageDTOList)
			.build();
	}

}
