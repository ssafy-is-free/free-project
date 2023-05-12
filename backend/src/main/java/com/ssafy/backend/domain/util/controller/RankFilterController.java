package com.ssafy.backend.domain.util.controller;

import static com.ssafy.backend.global.response.CustomSuccessStatus.*;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.domain.util.dto.LanguageResponse;
import com.ssafy.backend.domain.util.service.RankFilterService;
import com.ssafy.backend.global.response.DataResponse;
import com.ssafy.backend.global.response.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/filter")
public class RankFilterController {

	private final ResponseService responseService;
	private final RankFilterService rankFilterService;

	@GetMapping("/language")
	public DataResponse<List<LanguageResponse>> getLanguageList(
		@RequestParam(value = "type") String type) {

		List<LanguageResponse> languageResponses = rankFilterService.getLanguageList(type);

		return languageResponses.isEmpty()
			? responseService.getDataResponse(languageResponses, RESPONSE_NO_CONTENT)
			: responseService.getDataResponse(languageResponses, RESPONSE_SUCCESS);

	}
}
