package com.ssafy.backend.domain.job.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobApplyUpdateRequest {

	private long statusId;
	private String memo;
	private String dDayName;
	private String nextDate;
	private String objective;
}
