package com.ssafy.backend.domain.github.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenRequest {

	private long githubId; //깃허브 id
	private boolean openStatus; //공개, 비공개 상태.(true면 공개, false 비공개)

}
