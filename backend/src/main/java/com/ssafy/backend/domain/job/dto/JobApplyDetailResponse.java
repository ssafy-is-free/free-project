package com.ssafy.backend.domain.job.dto;

import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.ssafy.backend.domain.entity.JobHistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 취업현황 상세정보
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class JobApplyDetailResponse {

	private long postingId;
	private String postingName;
	private String companyName;
	private String status;
	private String startTime;
	private String endTime;
	private String memo;
	private String dDayName;
	private String nextDate;
	private String objective;
	private int applicantCount;

	public static JobApplyDetailResponse create(JobHistory jobHistory, int applicantCount,
		Map<Long, String> statusNameMap) {

		return JobApplyDetailResponse.builder()
			.postingId(jobHistory.getJobPosting().getId())
			.postingName(jobHistory.getJobPosting().getName())
			.companyName(jobHistory.getJobPosting().getCompanyName())
			.status(statusNameMap.get(jobHistory.getStatusId()))
			.startTime(jobHistory.getJobPosting().getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.endTime(jobHistory.getJobPosting().getEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.memo(jobHistory.getMemo())
			.dDayName(statusNameMap.get(jobHistory.getStatusId()))
			.nextDate(jobHistory.getDDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.objective(jobHistory.getJobObjective())
			.applicantCount(applicantCount)
			.build();
	}
}
