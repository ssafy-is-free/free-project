package com.ssafy.backend.domain.github.controller;

import com.ssafy.backend.domain.github.service.GithubCrawlingService;
import com.ssafy.backend.domain.github.service.GithubService;
import com.ssafy.backend.domain.user.dto.NicknameListResponseDTO;
import com.ssafy.backend.global.response.CommonResponse;
import com.ssafy.backend.global.response.CustomSuccessStatus;
import com.ssafy.backend.global.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.ssafy.backend.global.response.CustomSuccessStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/github")
public class GithubController {

    private final GithubService githubService;

    private final GithubCrawlingService crawlingService;

    private final ResponseService responseService;

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
