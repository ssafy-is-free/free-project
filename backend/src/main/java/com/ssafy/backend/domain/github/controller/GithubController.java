package com.ssafy.backend.domain.github.controller;

import static com.ssafy.backend.global.response.CustomSuccessStatus.*;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.domain.github.dto.GitHubRankingFilter;
import com.ssafy.backend.domain.github.dto.GithubDetailResponse;
import com.ssafy.backend.domain.github.dto.GithubRankingOneResponse;
import com.ssafy.backend.domain.github.dto.GithubRankingResponse;
import com.ssafy.backend.domain.github.dto.OpenRequest;
import com.ssafy.backend.domain.github.dto.ReadmeResponse;
import com.ssafy.backend.domain.github.service.GithubRankingService;
import com.ssafy.backend.domain.github.service.GithubService;
import com.ssafy.backend.domain.user.dto.NicknameListResponse;
import com.ssafy.backend.global.auth.dto.UserPrincipal;
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
	private final GithubRankingService githubRankingService;

	//깃허브 랭킹
	@GetMapping("/ranks")
	public DataResponse<GithubRankingResponse> getGithubRanks(@RequestParam(required = false) Long rank,
		@RequestParam(required = false) Long userId, @RequestParam(required = false) Integer score,
		@ModelAttribute GitHubRankingFilter rankingFilter,
		@PageableDefault(sort = "score", direction = Sort.Direction.ASC) Pageable pageable) {

		GithubRankingResponse githubRankingResponse = githubRankingService.getGithubRank(rank, userId, score,
			rankingFilter, pageable);

		return responseService.getDataResponse(githubRankingResponse, CustomSuccessStatus.RESPONSE_SUCCESS);
	}

	@GetMapping("/{githubId}/repositories/{repositoryId}")
	public DataResponse<ReadmeResponse> getReadme(@PathVariable long githubId, @PathVariable long repositoryId) {
		ReadmeResponse readmeResponse = githubService.getReadme(githubId, repositoryId);
		return responseService.getDataResponse(readmeResponse, RESPONSE_SUCCESS);
	}

	@GetMapping(value = {"/users/{userId}", "/users"})
	public DataResponse<GithubDetailResponse> getGithubDetails(@PathVariable(required = false) Long userId,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		long myUserId = userPrincipal != null ? userPrincipal.getId() : 0;
		userId = userId != null ? userId : myUserId;

		GithubDetailResponse details = githubService.getDetails(userId, userId == myUserId);
		return responseService.getDataResponse(details, RESPONSE_SUCCESS);

	}

	// TODO: 2023-05-04 깃허브 업데이트 스케쥴링할 때 다시 부활시키기
	// @PatchMapping("/crawling")
	// public CommonResponse githubCrawling(@AuthenticationPrincipal UserPrincipal userPrincipal) {
	//
	// 	String nickname = userPrincipal.getNickname();
	// 	long userId = userPrincipal.getId();
	//
	// 	crawlingService.getGithubInfo(nickname, userId);
	// 	return responseService.getSuccessResponse();
	// }

	@GetMapping("/search")
	public CommonResponse getNicknameList(@RequestParam String nickname) {
		List<NicknameListResponse> nicknameList = githubService.getNicknameList(nickname);
		return nicknameList.size() == 0 ?
			responseService.getDataResponse(Collections.emptyList(), RESPONSE_NO_CONTENT) :
			responseService.getDataResponse(nicknameList, RESPONSE_SUCCESS);
	}

	@GetMapping("/my-rank")
	public CommonResponse getMyGithubRank(@AuthenticationPrincipal UserPrincipal userPrincipal,
		GitHubRankingFilter rankingFilter) {
		GithubRankingOneResponse githubRankOne = githubRankingService.getGithubRankOne(userPrincipal.getId(),
			rankingFilter);
		return githubRankOne.getGithubRankingCover() == null ?
			responseService.getDataResponse(Collections.emptyList(), RESPONSE_NO_CONTENT) :
			responseService.getDataResponse(githubRankOne, RESPONSE_SUCCESS);
	}

	@GetMapping("/user-rank/{userId}")
	public CommonResponse searchGithubRank(@PathVariable long userId, GitHubRankingFilter rankingFilter) {
		GithubRankingOneResponse githubRankOne = githubRankingService.getGithubRankOne(userId, rankingFilter);
		return githubRankOne.getGithubRankingCover() == null ?
			responseService.getDataResponse(Collections.emptyList(), RESPONSE_NO_CONTENT) :
			responseService.getDataResponse(githubRankOne, RESPONSE_SUCCESS);
	}

	@PatchMapping("/open")
	public CommonResponse openGitRepository(
		@RequestBody OpenRequest openRequest,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {

		long userId = userPrincipal.getId();

		githubService.updatePublic(userId, openRequest);

		return responseService.getSuccessResponse();

	}

}
