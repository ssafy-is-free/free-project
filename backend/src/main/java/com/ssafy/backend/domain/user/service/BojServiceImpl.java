package com.ssafy.backend.domain.user.service;

import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.user.dto.BojIdRequestDTO;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.global.response.exception.CustomException;
import com.ssafy.backend.global.response.exception.CustomExceptionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.backend.global.response.exception.CustomExceptionStatus.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class BojServiceImpl implements BojService{

    private final UserRepository userRepository;


    @Override
    public void saveId(long userId, BojIdRequestDTO bojIdRequestDTO) {

        //유저 조회
        User user = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        
        //백준 ID 저장
        user.saveBojId(bojIdRequestDTO.getBojId());




    }

    @Override
    public void checkDuplicateId(String bojId) {

        //백준 ID로 유저 조회
        userRepository.findByBojIdAndIsDeletedFalse(bojId)
                .ifPresent(user -> new CustomException(BOJ_DUPLICATED));
    }


}
