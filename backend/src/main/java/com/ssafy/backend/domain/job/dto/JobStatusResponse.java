package com.ssafy.backend.domain.job.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.backend.domain.entity.JobStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class JobStatusResponse {

	private long id;
	private String name;

	public static JobStatusResponse create(JobStatus jobStatus) {
		return JobStatusResponse.builder()
			.id(jobStatus.getId())
			.name(jobStatus.getName())
			.build();
	}

	public static List<JobStatusResponse> createList(List<JobStatus> jobStatusList) {

		return jobStatusList.stream()
			.map(JobStatusResponse::create)
			.collect(Collectors.toList());
	}
}
