package com.ssafy.backend.domain.algorithm.controller;

import static com.ssafy.backend.global.response.CustomSuccessStatus.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.domain.algorithm.dto.response.BojMyRankResponseDTO;
import com.ssafy.backend.domain.algorithm.service.AlgorithmService;
import com.ssafy.backend.global.auth.dto.UserPrincipal;
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
@RequestMapping("/api/v1/boj")
public class AlgorithmController {
	private final ResponseService responseService;
	private final AlgorithmService algorithmService;

	@GetMapping("/my-rank")

	public DataResponse<BojMyRankResponseDTO> bojMyRank(@AuthenticationPrincipal UserPrincipal userPrincipal) {
		BojMyRankResponseDTO bojMyRankResponseDTO = algorithmService.getBojByUserId(userPrincipal.getId());
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

}
