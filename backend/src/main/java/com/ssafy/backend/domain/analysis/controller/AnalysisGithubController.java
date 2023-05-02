package com.ssafy.backend.domain.analysis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.domain.analysis.service.AnalysisGithubService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/analysis/github")
public class AnalysisGithubController {
	private final AnalysisGithubService analysisGithubService;
	
}
