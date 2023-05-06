package com.ssafy.backend.domain.job.dto;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ssafy.backend.domain.entity.JobHistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 취업 현황 조회
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class JobApplyResponse {

	private long jobHistoryId;
	private String companyName;
	private String dDayName;
	private String nextDate;
	private String dDay;
	private String status;

	public static JobApplyResponse create(JobHistory jobHistory, Map<Long, String> statusNameMap) {

		return JobApplyResponse.builder()
			.jobHistoryId(jobHistory.getId())
			.companyName(jobHistory.getJobPosting().getCompanyName())
			.dDayName(jobHistory.getDDayName())
			.nextDate(jobHistory.getDDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.dDay(String.valueOf(Period.between(LocalDate.now(), jobHistory.getDDay()).getDays()))
			.status(statusNameMap.get(jobHistory.getStatusId()))
			.build();

	}

	public static List<JobApplyResponse> createList(List<JobHistory> jobHistoryList, Map<Long, String> statusNameMap) {

		return jobHistoryList.stream()
			.map((jobHistory) -> JobApplyResponse.create(jobHistory, statusNameMap))
			.collect(Collectors.toList());
	}
}
