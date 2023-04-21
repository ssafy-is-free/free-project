package com.ssafy.backend.domain.algorithm.service;

import com.ssafy.backend.domain.algorithm.dto.request.BojInformationRequestDTO;
import com.ssafy.backend.domain.algorithm.dto.request.BojLanguageResultDTO;
import com.ssafy.backend.domain.algorithm.repository.BojLanguageRepository;
import com.ssafy.backend.domain.algorithm.repository.BojRepository;
import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.BaekjoonLanguage;
import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.entity.common.LanguageType;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.util.repository.LanguageRepository;
import com.ssafy.backend.global.response.exception.CustomException;
import com.ssafy.backend.global.response.exception.CustomExceptionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ssafy.backend.global.response.exception.CustomExceptionStatus.NOT_FOUND_USER;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlgorithmServiceImpl implements AlgorithmService{
    private final BojRepository bojRepository;
    private final BojLanguageRepository bojLanguageRepository;
    private final LanguageRepository languageRepository;
    private final UserRepository userRepository;
    private final WebClient webClient;
    @Override
    @Transactional
    public void postBojByUserId(long userId) throws Exception {
        //유저 아이디로 백준 아이디 조회
        Optional<User> oUser = userRepository.findById(userId);
        User user = oUser.orElseThrow(() -> new CustomException(NOT_FOUND_USER));



        //백준 아이디로 크롤링
        BojInformationRequestDTO bojInformationRequestDTO = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/data/baekjoon/{name}").build(user.getBojId()))
                .retrieve()
                .bodyToMono(BojInformationRequestDTO.class)
                .block();

        //백준 아이디로 비동기 크롤링
        /*Mono<BojInformationRequestDTO> mono = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/data/baekjoon/{name}").build(name))
                .exchangeToMono(clientResponse  -> clientResponse.toEntity(BojInformationRequestDTO.class)) // 요청 실행 후 Mono<ClientResponse> 반환
                .flatMap(responseEntity -> {
                    if(responseEntity.getStatusCode().is2xxSuccessful()){
                        return Mono.just(responseEntity.getBody());
                    }else{
                        return Mono.error(new RuntimeException("Unexpected response")); // 실패한 경우 에러 처리
                    }
                })
                .onErrorResume(e -> {
                    // 에러 처리 및 대체 DTO 반환
                    return getFallbackDto();
                });*/
        //저장
        if(bojInformationRequestDTO.getTier() != null){
            Baekjoon baekjoon = Baekjoon.builder()
                    .tier(bojInformationRequestDTO.getTier())
                    .passCount(bojInformationRequestDTO.getPassCount())
                    .tryFailCount(bojInformationRequestDTO.getTryFailCount())
                    .submitCount(bojInformationRequestDTO.getSubmitCount())
                    .failCount(bojInformationRequestDTO.getFailCount())
                    .user(user)
                    .build();
            bojRepository.save(baekjoon);
            // 리스트 저장
            // 리스트가 비어있지 않을 때
            if(bojInformationRequestDTO.getLanguagesResult() != null){
                List<BaekjoonLanguage> baekjoonLanguageList = new ArrayList<>();
                for(BojLanguageResultDTO bojLanguageResultDTO : bojInformationRequestDTO.getLanguagesResult()){

                    // 언어 정보 받아오기
                    Language language = languageRepository.findByNameAndType(bojLanguageResultDTO.getLanguage(), LanguageType.BAEKJOON);
                    BaekjoonLanguage baekjoonLanguage = BaekjoonLanguage.builder()
                            .languageId(language.getId())
                            .passPercentage(bojLanguageResultDTO.getPassPercentage())
                            .passCount(bojInformationRequestDTO.getPassCount())
                            .baekjoon(baekjoon)
                            .build();
                    baekjoonLanguageList.add(baekjoonLanguage);
                }
                bojLanguageRepository.saveAll(baekjoonLanguageList);
            }




        }



    }
}
