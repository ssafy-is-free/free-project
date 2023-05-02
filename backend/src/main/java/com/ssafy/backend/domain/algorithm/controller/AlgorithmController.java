package com.ssafy.backend.domain.algorithm.controller;

import static com.ssafy.backend.global.response.CustomSuccessStatus.*;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.domain.algorithm.dto.response.BojInfoDetailResponse;
import com.ssafy.backend.domain.algorithm.dto.response.BojRankResponse;
import com.ssafy.backend.domain.algorithm.service.AlgorithmService;
import com.ssafy.backend.domain.user.dto.NicknameListResponse;
import com.ssafy.backend.global.auth.dto.UserPrincipal;
import com.ssafy.backend.global.response.CommonResponse;
import com.ssafy.backend.global.response.DataResponse;
import com.ssafy.backend.global.response.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 알고리즘 관련 API 컨트롤러
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/boj")
public class AlgorithmController {
	private final ResponseService responseService;
	private final AlgorithmService algorithmService;

	// TODO: 2023-04-25 12시에 한꺼번에 배치할떄 사용할 백준 크롤링
	/*@PatchMapping("")
	public CommonResponse bojSaveUser(@RequestParam Long userId) {

		algorithmService.patchBojByUserId(userId);
		return responseService.getSuccessResponse();
	}*/

	@GetMapping("/my-rank")
	public DataResponse<BojRankResponse> bojMyRank(@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestParam(value = "languageId", required = false) Long languageId,
		@RequestParam(value = "jobPostingId", required = false) Long jobPostingId) {
		BojRankResponse bojMyRankResponse = algorithmService.getBojByUserId(userPrincipal.getId(), languageId,
			jobPostingId);
		//백준 아이디가 없다면 비어있는 컨텐츠
		return bojMyRankResponse.isEmpty() ? responseService.getDataResponse(null, RESPONSE_NO_CONTENT) :
			responseService.getDataResponse(bojMyRankResponse, RESPONSE_SUCCESS);
	}

	@GetMapping("/search")
	public CommonResponse getBojIdList(@RequestParam String nickname) {
		List<NicknameListResponse> bojIdList = algorithmService.getBojListByBojId(nickname);
		return bojIdList.size() == 0 ? responseService.getDataResponse(Collections.emptyList(), RESPONSE_NO_CONTENT) :
			responseService.getDataResponse(bojIdList, RESPONSE_SUCCESS);
	}

	@GetMapping("/user-rank/{userId}")
	public CommonResponse getBojId(@PathVariable Long userId,
		@RequestParam(value = "languageId", required = false) Long languageId) {
		BojRankResponse bojRankResponse = algorithmService.getBojByUserId(userId, languageId, null);

		return bojRankResponse.isEmpty() ? responseService.getDataResponse(null, RESPONSE_NO_CONTENT) :
			responseService.getDataResponse(bojRankResponse, RESPONSE_SUCCESS);
	}

	@GetMapping(value = {"/users/{userId}", "/users"})
	public DataResponse<BojInfoDetailResponse> getBojInfoDetail(@PathVariable(required = false) Long userId,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		userId = userId != null ? userId : userPrincipal.getId();
		BojInfoDetailResponse bojInfoDetailResponse = algorithmService.getBojInfoDetailByUserId(userId);
		return bojInfoDetailResponse.isEmpty() ? responseService.getDataResponse(null, RESPONSE_NO_CONTENT) :
			responseService.getDataResponse(bojInfoDetailResponse, RESPONSE_SUCCESS);
	}

	@GetMapping("ranks")
	public DataResponse<List<BojRankResponse>> getBojRank(
		@RequestParam(value = "group", required = false) String group,
		@RequestParam(value = "languageId", required = false) Long languageId,
		@RequestParam(value = "score", required = false) Integer score,
		@RequestParam(value = "userId", required = false) Long userId,
		@RequestParam(value = "rank", required = false) Long rank,
		Pageable pageable) {

		List<BojRankResponse> bojRankResponseList = algorithmService.getBojRankListByBojId(group, languageId,
			score, rank, userId, pageable);

		log.info("languageId : {}", languageId);
		log.info("score : {}", score);
		log.info("id : {}", userId);
		log.info("rank : {}", rank);
		log.info("size : {}", pageable.getPageSize());

		return bojRankResponseList.isEmpty() ?
			responseService.getDataResponse(bojRankResponseList, RESPONSE_NO_CONTENT) :
			responseService.getDataResponse(bojRankResponseList, RESPONSE_SUCCESS);
	}

}
