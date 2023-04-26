package com.ssafy.backend.domain.algorithm.service;

import java.util.List;

import com.ssafy.backend.domain.algorithm.dto.response.BojInfoDetailResponseDTO;
import com.ssafy.backend.domain.algorithm.dto.response.BojRankResponseDTO;
import com.ssafy.backend.domain.user.dto.NicknameListResponseDTO;

public interface AlgorithmService {
	void patchBojByUserId(long userId);

	BojRankResponseDTO getBojByUserId(long userId);

	List<NicknameListResponseDTO> getBojListByBojId(String nickname);

	BojInfoDetailResponseDTO getBojInfoDetailByUserId(Long userId);
}
