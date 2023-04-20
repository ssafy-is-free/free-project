package com.ssafy.backend.domain.algorithm.service;

import com.ssafy.backend.domain.algorithm.dto.request.BojInformationRequestDTO;
import com.ssafy.backend.domain.algorithm.dto.request.BojLanguageResultDTO;
import com.ssafy.backend.domain.algorithm.repository.BojRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlgorithmServiceImpl implements AlgorithmService{
    private final BojRepository bojRepository;
    private final WebClient webClient;
    @Override
    @Transactional
    public void postBojByUserId(long userId) throws Exception {
        String name = "sodamito";
        
        //유저 아이디로 백준 아이디 조회

        //백준 아이디로 크롤링
        BojInformationRequestDTO bojInformationRequestDTO = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/data/baekjoon/{name}").build(name))
                .retrieve()
                .bodyToMono(BojInformationRequestDTO.class)
                .block();
        
        
        
        //저장



    }
}
