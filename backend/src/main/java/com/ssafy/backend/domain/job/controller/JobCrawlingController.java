package com.ssafy.backend.domain.job.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.domain.job.service.JobCrawlingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JobCrawlingController {

	private final JobCrawlingService jobCrawlingService;

	@GetMapping("/job/crawling")
	public void getJobPostings() {
		jobCrawlingService.crawlingJobPostings();
	}
}
