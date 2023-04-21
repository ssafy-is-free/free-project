package com.ssafy.backend.domain.github.controller;

import com.ssafy.backend.domain.github.service.GithubCrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/github")
public class GithubController {

    private final GithubCrawlingService crawlingService;

    @PatchMapping("/crawling/{nickname}")
    public void githubCrawling(@PathVariable String nickname) {
        crawlingService.get(nickname, 1L);
    }
}
