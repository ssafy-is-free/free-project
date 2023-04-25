package com.ssafy.backend.domain.github.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.domain.github.dto.GithubRankingResponse;
import com.ssafy.backend.domain.github.service.GithubService;
import com.ssafy.backend.global.response.CustomSuccessStatus;
import com.ssafy.backend.global.response.DataResponse;
import com.ssafy.backend.global.response.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/github")
public class GithubController {

	private final ResponseService responseService;
	private final GithubService githubService;

	@GetMapping("/ranks")
	public DataResponse<GithubRankingResponse> getGithubRanks(long rank, Long githubId, Integer score,
		@PageableDefault(sort = "score", direction = Sort.Direction.ASC) Pageable pageable) {

		GithubRankingResponse githubRankingResponse = githubService.getGithubRank(rank, githubId, score, pageable);

		return responseService.getDataResponse(githubRankingResponse, CustomSuccessStatus.RESPONSE_SUCCESS);
	}
}
