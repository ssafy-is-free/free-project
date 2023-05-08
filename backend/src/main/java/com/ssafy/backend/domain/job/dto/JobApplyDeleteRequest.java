package com.ssafy.backend.domain.job.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 선택 된 취업현황 삭제.
 */
@Getter
@Setter
public class JobApplyDeleteRequest {

	private List<Long> historyId;
}
