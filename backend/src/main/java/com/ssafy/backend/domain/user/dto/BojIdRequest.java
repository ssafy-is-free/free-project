package com.ssafy.backend.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class BojIdRequest {

	@JsonProperty("boj_id")
	private String bojId;
}
