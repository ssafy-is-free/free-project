package com.ssafy.backend.domain.job.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobApplyUpdateRequest {

	private Long statusId;
	private String memo;
	private String dDayName;
	private String nextDate;
	private String objective;
}
