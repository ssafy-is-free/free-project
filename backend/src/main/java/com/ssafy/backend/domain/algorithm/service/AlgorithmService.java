package com.ssafy.backend.domain.algorithm.service;

import java.util.List;

import com.ssafy.backend.domain.algorithm.dto.response.BojRankResponseDTO;
import com.ssafy.backend.domain.user.dto.NicknameListResponse;

public interface AlgorithmService {
	void patchBojByUserId(long userId);

	BojRankResponseDTO getBojByUserId(long userId);

	List<NicknameListResponse> getBojListByBojId(String nickname);

}
