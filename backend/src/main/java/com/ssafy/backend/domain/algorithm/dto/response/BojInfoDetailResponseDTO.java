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
public class BojInfoDetailResponseDTO {
	private String bojId;
	private String tierUrl;
	private int pass;
	private int tryFail;
	private int submit;
	private int fail;
	private List<BojLanguageDTO> languages;

	public static BojInfoDetailResponseDTO create(User user, Baekjoon baekjoon, List<BojLanguageDTO> languageDTOList) {
		return BojInfoDetailResponseDTO.builder()
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
