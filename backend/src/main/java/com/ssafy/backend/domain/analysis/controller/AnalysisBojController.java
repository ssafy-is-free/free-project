package com.ssafy.backend.domain.analysis.controller;

import static com.ssafy.backend.global.response.CustomSuccessStatus.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.domain.analysis.dto.BojRankAllComparison;
import com.ssafy.backend.domain.analysis.dto.BojRankComparison;
import com.ssafy.backend.domain.analysis.service.AnalysisBojService;
import com.ssafy.backend.global.auth.dto.UserPrincipal;
import com.ssafy.backend.global.response.DataResponse;
import com.ssafy.backend.global.response.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/analysis/boj")
public class AnalysisBojController {

	private final AnalysisBojService analysisBojService;
	private final ResponseService responseService;

	@GetMapping("/users/{userId}")
	public DataResponse<BojRankComparison> bojCompareWithOpponent(@AuthenticationPrincipal UserPrincipal userPrincipal,
		@PathVariable Long userId) {
		BojRankComparison bojRankComparison = analysisBojService.compareWithOpponent(userPrincipal.getId(), userId);
		//백준 아이디가 없다면 비어있는 컨텐츠
		return bojRankComparison.checkForNull() ? responseService.getDataResponse(null, RESPONSE_NO_CONTENT) :
			responseService.getDataResponse(bojRankComparison, RESPONSE_SUCCESS);
	}

	@GetMapping("/postings/{jobPostingId}")
	public DataResponse<BojRankAllComparison> bojbojCompareWithOther(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@PathVariable Long jobPostingId) {
		BojRankAllComparison bojRankAllComparison = analysisBojService.compareWithOther(
			userPrincipal.getId(), jobPostingId);
		//백준 아이디가 없다면 비어있는 컨텐츠
		return bojRankAllComparison.checkForNull() ? responseService.getDataResponse(null, RESPONSE_NO_CONTENT) :
			responseService.getDataResponse(bojRankAllComparison, RESPONSE_SUCCESS);
	}
}
