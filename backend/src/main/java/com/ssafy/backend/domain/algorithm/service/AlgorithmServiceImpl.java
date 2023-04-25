package com.ssafy.backend.domain.algorithm.service;

import com.ssafy.backend.domain.algorithm.dto.response.BojInformationResponseDTO;
import com.ssafy.backend.domain.algorithm.dto.response.BojLanguageResultDTO;
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
    public void patchBojByUserId(long userId){
        //유저 아이디로 백준 아이디 조회
        Optional<User> oUser = userRepository.findById(userId);
        User user = oUser.orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        //백준 아이디로 크롤링
        BojInformationResponseDTO bojInformationResponseDTO = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/data/baekjoon/{name}").build(user.getBojId()))
                .retrieve()
                .bodyToMono(BojInformationResponseDTO.class)
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
        if(bojInformationResponseDTO.getTier() != null){
            //유저가 이미 백준 아이디를 저장했는지 확인하기
            Optional<Baekjoon> oBaekjoon = bojRepository.findByUserId(userId);
            Baekjoon baekjoon = oBaekjoon.orElse(null);
            // 비어있다면 추가하고 이미 있다면 업데이트
            if(baekjoon == null){
                baekjoon = Baekjoon.createBaekjoon(bojInformationResponseDTO, user);
            }else {
                baekjoon.updateBaekjoon(bojInformationResponseDTO);
            }
            bojRepository.save(baekjoon);
            // 리스트 저장
            // 리스트가 비어있지 않을 때
            if(bojInformationResponseDTO.getLanguagesResult() != null){
                List<BaekjoonLanguage> baekjoonLanguageList = new ArrayList<>();
                for(BojLanguageResultDTO bojLanguageResultDTO : bojInformationResponseDTO.getLanguagesResult()){

                    // 언어 정보 받아오기
                    Language language = languageRepository.findByNameAndType(bojLanguageResultDTO.getLanguage(), LanguageType.BAEKJOON).orElseGet(
                            () -> null  // 언어정보가 없다면 언어 생성, 저장, 반환 2023-04-21 이성복
                    );
                    // 유저에 해당하는 언어 정보가 있는지 파악하기
                    Optional<BaekjoonLanguage> oBaekjoonLanguage = bojLanguageRepository.findByLanguageIdAndBaekjoonId(language.getId(), baekjoon.getId());                    //
                    BaekjoonLanguage baekjoonLanguage = oBaekjoonLanguage.orElse(null);
                    // 비어있다면 추가하고 이미 있다면 업데이트
                    if(baekjoonLanguage == null){
                        baekjoonLanguage = BaekjoonLanguage.createBaekjoonLanguage(language.getId(), bojLanguageResultDTO, baekjoon);
                    }else{
                        baekjoonLanguage.updateBaekjoonLanguage(bojLanguageResultDTO);
                    }
                    baekjoonLanguageList.add(baekjoonLanguage);
                }
                bojLanguageRepository.saveAll(baekjoonLanguageList);
            }
        }
    }
}
