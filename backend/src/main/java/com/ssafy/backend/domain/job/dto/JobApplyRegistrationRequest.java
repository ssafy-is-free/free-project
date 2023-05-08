package com.ssafy.backend.domain.job.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 취업현황 등록
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobApplyRegistrationRequest {

	private long statusId;
	private long jobPostingId;
	private String objective;
	private String memo;
	private String dDay;
	private String dDayName;

}
