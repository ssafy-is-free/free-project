package com.ssafy.backend.domain.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.domain.user.dto.BojIdRequest;
import com.ssafy.backend.domain.user.service.BojService;
import com.ssafy.backend.global.auth.dto.UserPrincipal;
import com.ssafy.backend.global.response.CommonResponse;
import com.ssafy.backend.global.response.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class BojController {

	private final BojService bojService;
	private final ResponseService responseService;

	@PatchMapping("boj-id")
	public CommonResponse saveBojId(
		@RequestBody BojIdRequest bojIdRequest,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {

		long userId = userPrincipal.getId();

		bojService.saveId(userId, bojIdRequest);

		return responseService.getSuccessResponse();
	}

	@GetMapping("boj-id")
	public CommonResponse checkBojId(
		@RequestParam("id") String bojId) {

		bojService.checkDuplicateId(bojId.trim());

		return responseService.getSuccessResponse();
	}
}
