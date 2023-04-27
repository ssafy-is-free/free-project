package com.ssafy.backend.domain.algorithm.service;

import java.util.List;

import com.ssafy.backend.domain.algorithm.dto.response.BojInfoDetailResponse;
import com.ssafy.backend.domain.algorithm.dto.response.BojRankResponse;
import com.ssafy.backend.domain.user.dto.NicknameListResponse;

public interface AlgorithmService {
	void patchBojByUserId(long userId);

	BojRankResponse getBojByUserId(long userId);

	List<NicknameListResponse> getBojListByBojId(String nickname);

	BojInfoDetailResponse getBojInfoDetailByUserId(Long userId);
}
