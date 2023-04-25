package com.ssafy.backend.domain.github.controller;

import static com.ssafy.backend.global.response.CustomSuccessStatus.*;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.domain.github.dto.GithubDetailResponse;
import com.ssafy.backend.domain.github.dto.GithubRankingResponse;
import com.ssafy.backend.domain.github.dto.ReadmeResponse;
import com.ssafy.backend.domain.github.service.GithubCrawlingService;
import com.ssafy.backend.domain.github.service.GithubService;
import com.ssafy.backend.domain.user.dto.NicknameListResponseDTO;
import com.ssafy.backend.global.response.CommonResponse;
import com.ssafy.backend.global.response.CustomSuccessStatus;
import com.ssafy.backend.global.response.DataResponse;
import com.ssafy.backend.global.response.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/github")
public class GithubController {

	private final ResponseService responseService;
	private final GithubService githubService;
	private final GithubCrawlingService crawlingService;

	//깃허브 랭킹
	@GetMapping("/ranks")
	public DataResponse<GithubRankingResponse> getGithubRanks(long rank, Long userId, Integer score,
		@PageableDefault(sort = "score", direction = Sort.Direction.ASC) Pageable pageable) {

		GithubRankingResponse githubRankingResponse = githubService.getGithubRank(rank, userId, score, pageable);

		return responseService.getDataResponse(githubRankingResponse, CustomSuccessStatus.RESPONSE_SUCCESS);
	}

	@GetMapping("/{githubId}/repositories/{repositoryId}")
	public DataResponse<ReadmeResponse> getReadme(@PathVariable long githubId, @PathVariable long repositoryId) {
		ReadmeResponse readmeResponse = githubService.getReadme(githubId, repositoryId);
		return responseService.getDataResponse(readmeResponse, RESPONSE_SUCCESS);
	}

	@GetMapping("/users/{userId}")
	public DataResponse<GithubDetailResponse> getGithubDetails(@PathVariable long userId) {
		GithubDetailResponse details = githubService.getDetails(userId);
		return responseService.getDataResponse(details, RESPONSE_SUCCESS);

	}

	@PatchMapping("/crawling/{nickname}/{userId}")
	public CommonResponse githubCrawling(@PathVariable String nickname, @PathVariable long userId) {
		crawlingService.getGithubInfo(nickname, userId);
		return responseService.getSuccessResponse();
	}

	@GetMapping("/search")
	public CommonResponse getNicknameList(@RequestParam String nickname) {
		List<NicknameListResponseDTO> nicknameList = githubService.getNicknameList(nickname);
		return nicknameList.size() == 0 ?
			responseService.getDataResponse(Collections.emptyList(), RESPONSE_NO_CONTENT) :
			responseService.getDataResponse(nicknameList, RESPONSE_SUCCESS);
	}
}
