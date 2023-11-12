package com.chop.subdata.controller;

import com.chop.subdata.dto.GithubDataDto;
import com.chop.subdata.service.GithubService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GithubController {

  private final GithubService githubService;

  @GetMapping("/data/github")
  public GithubDataDto getGithubData(@RequestHeader("access-token") String accessToken) throws JsonProcessingException {
    return githubService.getGithubData(accessToken);
  }
}
