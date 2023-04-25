package com.ssafy.backend.domain.algorithm.controller;

import static com.ssafy.backend.global.response.CustomSuccessStatus.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.domain.algorithm.dto.response.BojMyRankResponseDTO;
import com.ssafy.backend.domain.algorithm.service.AlgorithmService;
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
@RequestMapping("/api/v1/boj")
public class AlgorithmController {
	private final ResponseService responseService;
	private final AlgorithmService algorithmService;s

	@GetMapping("/my-rank")
	public DataResponse<BojMyRankResponseDTO> bojMyRank(@AuthenticationPrincipal UserPrincipal userPrincipal) {
		BojMyRankResponseDTO bojMyRankResponseDTO = algorithmService.getBojByUserId(userPrincipal.getId());

		return responseService.getDataResponse(bojMyRankResponseDTO, RESPONSE_SUCCESS);
	}

	@PatchMapping("")
	public CommonResponse bojSaveUser(@RequestParam Long userId) {

		algorithmService.patchBojByUserId(userId);

		return responseService.getSuccessResponse();
	}

}
