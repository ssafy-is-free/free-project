package com.ssafy.backend.domain.algorithm.controller;

import com.ssafy.backend.domain.algorithm.dto.response.BojMyRankResponseDTO;
import com.ssafy.backend.domain.algorithm.service.AlgorithmService;
import com.ssafy.backend.global.response.CommonResponse;
import com.ssafy.backend.global.response.DataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 알고리즘 관련 API 컨트롤러
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/boj")
public class AlgorithmController {
    private final AlgorithmService algorithmService;
    @GetMapping("/my-rank")
    public DataResponse<BojMyRankResponseDTO> bojMyRank(){

        return null;
    }
    @PostMapping("/{userId}")
    public CommonResponse bojSaveUser(@PathVariable("userId") Long userId) throws Exception{
        algorithmService.postBojByUserId(userId);
        return null;
    }
}
