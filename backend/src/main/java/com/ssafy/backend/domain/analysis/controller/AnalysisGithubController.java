package com.ssafy.backend.domain.analysis.controller;

import static com.ssafy.backend.global.response.CustomSuccessStatus.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.domain.analysis.dto.response.CompareGithubResponse;
import com.ssafy.backend.domain.analysis.service.AnalysisGithubService;
import com.ssafy.backend.global.auth.dto.UserPrincipal;
import com.ssafy.backend.global.response.CommonResponse;
import com.ssafy.backend.global.response.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/analysis/github")
public class AnalysisGithubController {
	private final ResponseService responseService;
	private final AnalysisGithubService analysisGithubService;

	@GetMapping("/users/{userId}")
	public CommonResponse compareWithOpponent(@PathVariable("userId") long opponentUserId,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		long myUserId = userPrincipal.getId();
		CompareGithubResponse compareGithubResponse = analysisGithubService.compareWithOpponent(opponentUserId,
			myUserId);

		return responseService.getDataResponse(compareGithubResponse, RESPONSE_SUCCESS);
	}

	@GetMapping("/postings/{jobPostingId}")
	public CommonResponse compareWithApplicant(@PathVariable("jobPostingId") long jobPostingId,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		long myUserId = userPrincipal.getId();
		CompareGithubResponse compareGithubResponse = analysisGithubService.compareWithAllApplicant(jobPostingId,
			myUserId);

		return responseService.getDataResponse(compareGithubResponse, RESPONSE_SUCCESS);
	}

}
