package com.ssafy.backend.domain.algorithm.service;

import com.ssafy.backend.domain.algorithm.dto.response.BojMyRankResponseDTO;

public interface AlgorithmService {
    void patchBojByUserId(long userId);

    BojMyRankResponseDTO getBojByUserId(long userId);
}
