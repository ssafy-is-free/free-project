package com.ssafy.backend.domain.util.controller;

import static com.ssafy.backend.global.response.CustomSuccessStatus.*;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.domain.util.dto.LanguageDTO;
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
	public DataResponse<List<LanguageDTO>> getLanguageList(
		@RequestParam(value = "type") String type) {

		List<LanguageDTO> languageDTOS = rankFilterService.getLanguageList(type);

		// TODO: 2023-04-24 삼항연산자로 개선
		if (languageDTOS.isEmpty())
			return responseService.getDataResponse(languageDTOS, RESPONSE_NO_CONTENT);

		return responseService.getDataResponse(languageDTOS, RESPONSE_SUCCESS);
	}
}
