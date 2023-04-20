package com.ssafy.backend.domain.algorithm.service;

import com.ssafy.backend.domain.algorithm.dto.request.BojInformationRequestDTO;
import com.ssafy.backend.domain.algorithm.dto.request.BojLanguageResultDTO;
import com.ssafy.backend.domain.algorithm.repository.BojRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlgorithmServiceImpl implements AlgorithmService{
    private final BojRepository bojRepository;
    @Override
    @Transactional
    public void postBojByUserId(long userId) throws Exception {


        BojInformationRequestDTO bojInformationRequestDTO =  null;
        List<BojLanguageResultDTO> bojLanguageResultDTOList = bojInformationRequestDTO.getLanguagesResult();


    }
}
