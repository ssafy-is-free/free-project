package com.ssafy.backend.domain.algorithm.service;

import com.ssafy.backend.domain.algorithm.dto.request.BojInformationRequestDTO;

public interface AlgorithmService {
    void postBojByUserId(long userId) throws Exception;
}
