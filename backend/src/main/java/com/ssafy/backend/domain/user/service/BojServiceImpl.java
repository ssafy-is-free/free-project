package com.ssafy.backend.domain.user.service;

import static com.ssafy.backend.global.response.exception.CustomExceptionStatus.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.ssafy.backend.domain.algorithm.dto.response.CBojInfoResponse;
import com.ssafy.backend.domain.algorithm.dto.response.CBojLanguageResultResponse;
import com.ssafy.backend.domain.algorithm.repository.BojLanguageRepository;
import com.ssafy.backend.domain.algorithm.repository.BojRepository;
import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.BaekjoonLanguage;
import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.entity.common.LanguageType;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.util.repository.LanguageRepository;
import com.ssafy.backend.domain.util.service.BojScoreEvaluator;
import com.ssafy.backend.global.response.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class BojServiceImpl implements BojService {

	private final UserRepository userRepository;
	private final BojRepository bojRepository;
	private final LanguageRepository languageRepository;
	private final BojLanguageRepository bojLanguageRepository;
	private final WebClient webClient;

	/**
	 * @param userId 유저의 아이디
	 * @apiNote 백준 아이디를 저장하고 크롤링한 데이터를 저장하는 메소드
	 * @author noobsoda
	 * @param bojId 백준 아이디 요청 객체
	 * @throws CustomException 사용자를 찾을 수 없거나, 백준 아이디가 조회되지 않을 때 발생
	 */
	@Override
	public void saveId(long userId, String bojId) {

		//백준 ID가 비어있을 경우
		if (bojId.isBlank()) {
			throw new CustomException(NOT_FOUND_BOJ_USER);
		}

		//유저 조회
		User user = userRepository.findByIdAndIsDeletedFalse(userId)
			.orElseThrow(() -> new CustomException(NOT_FOUND_USER));

		//백준 ID 저장
		user.saveBojId(bojId);
		userRepository.save(user);

		//백준 아이디로 크롤링
		CBojInfoResponse CBojInfoResponse = webClient.get()
			.uri(uriBuilder -> uriBuilder.path("/data/baekjoon/{name}").build(bojId))
			.retrieve()
			.bodyToMono(CBojInfoResponse.class)
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
		//백준 아이디가 조회될 때만 저장
		if (CBojInfoResponse == null || CBojInfoResponse.checkForNull()) {
			//입력한 백준아아디가 크롤링이 되지 않았거나 올바르지 않습니다.
			throw new CustomException(NOT_FOUND_BOJ_USER);
		} else {
			//유저가 이미 백준 아이디를 저장했는지 확인하기
			Optional<Baekjoon> oBaekjoon = bojRepository.findByUser(user);
			Baekjoon baekjoon = null;
			if (oBaekjoon.isPresent()) {
				baekjoon = oBaekjoon.get();
			}
			// 백준 스코어 저장 로직
			int score = BojScoreEvaluator.scoreEvaluator(CBojInfoResponse);
			// 비어있다면 추가하고 이미 있다면 업데이트
			if (baekjoon == null) {
				baekjoon = Baekjoon.createBaekjoon(CBojInfoResponse, user, score);
			} else {
				baekjoon.updateBaekjoon(CBojInfoResponse, score);
			}

			bojRepository.save(baekjoon);
			// 리스트 저장
			// 리스트가 비어있지 않을 때
			if (CBojInfoResponse.getLanguagesResult() != null) {
				List<BaekjoonLanguage> baekjoonLanguageList = new ArrayList<>();
				bojLanguageRepository.deleteAllByBaekjoonId(baekjoon.getId());

				for (CBojLanguageResultResponse CBojLanguageResultResponse : CBojInfoResponse.getLanguagesResult()) {

					// 언어 정보 받아오기
					Language language = languageRepository.findByNameAndType(CBojLanguageResultResponse.getLanguage(),
						LanguageType.BAEKJOON).orElseGet(
						() -> null  // 언어정보가 없다면 언어 생성, 저장, 반환 2023-04-21 이성복
					);

					BaekjoonLanguage baekjoonLanguage = BaekjoonLanguage.createBaekjoonLanguage(language.getId(),
						CBojLanguageResultResponse, baekjoon);
					baekjoonLanguageList.add(baekjoonLanguage);
				}
				bojLanguageRepository.saveAll(baekjoonLanguageList);
			}
		}

	}

	@Override
	public void checkDuplicateId(String bojId) {

		//백준 ID로 유저 조회
		// userRepository.findUserByBojIdAndIsDeletedFalse(bojId)
		// 	.ifPresent(user -> new CustomException(BOJ_DUPLICATED));

		if (userRepository.findUserByBojIdAndIsDeletedFalse(bojId).isPresent()) {
			throw new CustomException(BOJ_DUPLICATED);
		}

	}

}
