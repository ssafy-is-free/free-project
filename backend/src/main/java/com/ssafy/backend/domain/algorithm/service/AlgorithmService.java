package com.ssafy.backend.domain.algorithm.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ssafy.backend.domain.algorithm.dto.response.BojInfoDetailResponse;
import com.ssafy.backend.domain.algorithm.dto.response.BojRankResponse;
import com.ssafy.backend.domain.user.dto.NicknameListResponse;

public interface AlgorithmService {
	BojRankResponse getBojByUserId(Long userId, Long languageId, Long jobPostingId);

	List<NicknameListResponse> getBojListByBojId(String nickname);

	BojInfoDetailResponse getBojInfoDetailByUserId(Long userId);

	// TODO: 2023-04-28 인자로 넘기는 값이 너무 많음, 필터 클래스를 생성해서 줄여야됨.
	/*백준 랭킹*/
	List<BojRankResponse> getBojRankListByBojId(String group, Long languageId, Integer score, Long rank,
		Long userId,
		Long jobPostingId,
		Pageable pageable);

}
