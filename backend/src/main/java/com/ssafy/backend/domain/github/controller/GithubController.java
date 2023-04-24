package com.ssafy.backend.domain.github.controller;

import com.ssafy.backend.domain.github.service.GithubCrawlingService;
import com.ssafy.backend.global.response.CommonResponse;
import com.ssafy.backend.global.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/github")
public class GithubController {

    private final GithubCrawlingService crawlingService;

    private final ResponseService responseService;

    @PatchMapping("/crawling/{nickname}/{userId}")
    public CommonResponse githubCrawling(@PathVariable String nickname, @PathVariable long userId) {
        crawlingService.getGithubInfo(nickname, userId);
        return responseService.getSuccessResponse();
    }
}
