package com.ssafy.backend.domain.algorithm.controller;

import static com.ssafy.backend.global.response.CustomSuccessStatus.*;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.domain.algorithm.dto.response.BojRankResponseDTO;
import com.ssafy.backend.domain.algorithm.service.AlgorithmService;
import com.ssafy.backend.domain.user.dto.NicknameListResponseDTO;
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

	@GetMapping("/my-rank")

	public DataResponse<BojRankResponseDTO> bojMyRank(@AuthenticationPrincipal UserPrincipal userPrincipal) {
		BojRankResponseDTO bojMyRankResponseDTO = algorithmService.getBojByUserId(userPrincipal.getId());
		//백준 아이디가 없다면 비어있는 컨텐츠
		return bojMyRankResponseDTO == null ? responseService.getDataResponse(null, RESPONSE_NO_CONTENT) :
			responseService.getDataResponse(bojMyRankResponseDTO, RESPONSE_SUCCESS);
	}

	// TODO: 2023-04-25 12시에 한꺼번에 배치할떄 사용할 백준 크롤링
	/*@PatchMapping("")
	public CommonResponse bojSaveUser(@RequestParam Long userId) {

		algorithmService.patchBojByUserId(userId);
		return responseService.getSuccessResponse();
	}*/
	@GetMapping("/search")
	public CommonResponse getBojIdList(@RequestParam String nickname) {
		List<NicknameListResponseDTO> bojIdList = algorithmService.getBojListByBojId(nickname);
		return bojIdList.size() == 0 ? responseService.getDataResponse(Collections.emptyList(), RESPONSE_NO_CONTENT) :
			responseService.getDataResponse(bojIdList, RESPONSE_SUCCESS);
	}

	@GetMapping("/user-rank/{userId}")
	public CommonResponse getBojId(@PathVariable("userId") Long userId) {
		BojRankResponseDTO bojRankResponseDTO = algorithmService.getBojByUserId(userId);
		return bojRankResponseDTO == null ? responseService.getDataResponse(null, RESPONSE_NO_CONTENT) :
			responseService.getDataResponse(bojRankResponseDTO, RESPONSE_SUCCESS);
	}

}
