package com.ssafy.backend.domain.job.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.ssafy.backend.domain.entity.JobPosting;
import com.ssafy.backend.domain.job.dto.CJobPosting;
import com.ssafy.backend.domain.job.repository.JobPostingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class JobCrawlingService {

	private final WebClient webClient;
	private final JobPostingRepository jobPostingRepository;

	public void crawlingJobPostings() {
		CJobPosting[] response = webClient.get()
			.uri("/data/postings")
			.retrieve()
			.bodyToMono(CJobPosting[].class)
			.block();

		List<JobPosting> jobPostingList = Arrays.stream(response)
			.map(p -> JobPosting.create(p))
			.collect(Collectors.toList());
		jobPostingRepository.saveAll(jobPostingList);
	}
}
