package com.ssafy.backend.domain.algorithm.service;

import java.util.List;

import com.ssafy.backend.domain.algorithm.dto.response.BojMyRankResponseDTO;
import com.ssafy.backend.domain.user.dto.BojIdListResponseDTO;

public interface AlgorithmService {
	void patchBojByUserId(long userId);

	BojMyRankResponseDTO getBojByUserId(long userId);

	List<BojIdListResponseDTO> getBojList(String nickname);
}
