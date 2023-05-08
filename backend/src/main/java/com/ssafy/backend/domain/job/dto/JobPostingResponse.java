package com.ssafy.backend.domain.job.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.backend.domain.entity.JobPosting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class JobPostingResponse {

	private long jobPostingId;
	private String companyName;
	private String postingName;
	private String startTime;
	private String endTime;

	// TODO: 2023-05-04 변환식 메서드로 뺄지 고민 필요.
	public static JobPostingResponse create(JobPosting jobPosting) {

		String convertStartTime = jobPosting.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String convertEndTime = jobPosting.getEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		return JobPostingResponse.builder()
			.jobPostingId(jobPosting.getId())
			.companyName(jobPosting.getCompanyName())
			.postingName(jobPosting.getName())
			.startTime(convertStartTime)
			.endTime(convertEndTime)
			.build();
	}

	public static List<JobPostingResponse> createList(List<JobPosting> jobPostingList) {

		return jobPostingList.stream()
			.map(JobPostingResponse::create)
			.collect(Collectors.toList());
	}
}
