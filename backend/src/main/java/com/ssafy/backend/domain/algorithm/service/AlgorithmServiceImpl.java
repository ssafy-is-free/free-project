package com.ssafy.backend.domain.algorithm.service;

import static com.ssafy.backend.global.response.exception.CustomExceptionStatus.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.ssafy.backend.domain.algorithm.dto.response.BojInfoDetailResponseDTO;
import com.ssafy.backend.domain.algorithm.dto.response.BojInfoResponseDTO;
import com.ssafy.backend.domain.algorithm.dto.response.BojLanguageDTO;
import com.ssafy.backend.domain.algorithm.dto.response.BojLanguageResultDTO;
import com.ssafy.backend.domain.algorithm.dto.response.BojRankResponseDTO;
import com.ssafy.backend.domain.algorithm.repository.BojLanguageRepository;
import com.ssafy.backend.domain.algorithm.repository.BojRepository;
import com.ssafy.backend.domain.algorithm.repository.BojRepositorySupport;
import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.BaekjoonLanguage;
import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.entity.common.LanguageType;
import com.ssafy.backend.domain.user.dto.NicknameListResponseDTO;
import com.ssafy.backend.domain.user.repository.UserQueryRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.util.repository.LanguageRepository;
import com.ssafy.backend.global.response.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlgorithmServiceImpl implements AlgorithmService {
	private final BojRepository bojRepository;
	private final BojLanguageRepository bojLanguageRepository;
	private final LanguageRepository languageRepository;
	private final UserRepository userRepository;
	private final WebClient webClient;
	private final UserQueryRepository userQueryRepository;
	private final BojRepositorySupport bojRepositorySupport;

	@Override
	@Transactional
	// TODO: 2023-04-24 나중에 12시에 한번에 배치할 때 사용할것
	public void patchBojByUserId(long userId) {
		//유저 아이디로 백준 아이디 조회
		Optional<User> oUser = userRepository.findById(userId);
		User user = oUser.orElseThrow(() -> new CustomException(NOT_FOUND_USER));

		//백준 아이디로 크롤링
		BojInfoResponseDTO bojInfoResponseDTO = webClient.get()
			.uri(uriBuilder -> uriBuilder.path("/api/data/baekjoon/{name}").build(user.getBojId()))
			.retrieve()
			.bodyToMono(BojInfoResponseDTO.class)
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
		if (bojInfoResponseDTO.getTier() != null) {
			//유저가 이미 백준 아이디를 저장했는지 확인하기
			Optional<Baekjoon> oBaekjoon = bojRepository.findByUserId(userId);
			Baekjoon baekjoon = oBaekjoon.orElse(null);
			// 비어있다면 추가하고 이미 있다면 업데이트
			if (baekjoon == null) {
				baekjoon = Baekjoon.createBaekjoon(bojInfoResponseDTO, user);
			} else {
				baekjoon.updateBaekjoon(bojInfoResponseDTO);
			}
			bojRepository.save(baekjoon);
			// 리스트 저장
			// 리스트가 비어있지 않을 때
			if (bojInfoResponseDTO.getLanguagesResult() != null) {
				List<BaekjoonLanguage> baekjoonLanguageList = new ArrayList<>();
				bojLanguageRepository.deleteAllByBaekjoonId(baekjoon.getId());

				for (BojLanguageResultDTO bojLanguageResultDTO : bojInfoResponseDTO.getLanguagesResult()) {

					// 언어 정보 받아오기
					Language language = languageRepository.findByNameAndType(bojLanguageResultDTO.getLanguage(),
						LanguageType.BAEKJOON).orElseGet(
						() -> null  // 언어정보가 없다면 언어 생성, 저장, 반환 2023-04-21 이성복
					);

					BaekjoonLanguage baekjoonLanguage = BaekjoonLanguage.createBaekjoonLanguage(language.getId(),
						bojLanguageResultDTO, baekjoon);
					baekjoonLanguageList.add(baekjoonLanguage);
				}
				bojLanguageRepository.saveAll(baekjoonLanguageList);
			}
		}
	}

	/**
	 * 이 메소드는 주어진 유저 아이디를 기반으로 해당 유저의 랭킹 정보를 반환합니다.
	 *
	 * @param userId 조회하려는 유저의 아이디 (정수 형태)
	 * @author noobsoda
	 * @return BojMyRankResponseDTO 해당 유저의 랭킹 정보를 담고 있는 DTO 객체
	 * @throws CustomException 유저 아이디가 잘못된 경우 발생하는 예외
	 */

	@Override
	public BojRankResponseDTO getBojByUserId(long userId) {
		int rank = 1;
		//유저 아이디로 백준 아이디 조회
		Optional<User> oUser = userRepository.findById(userId);
		User user = oUser.orElseThrow(() -> new CustomException(NOT_FOUND_USER));

		//백준 아이디 없다면 돌아가기
		if (user.getBojId() == null) {
			return null;
		}
		List<Baekjoon> baekjoonList = bojRepository.findAllByOrderByScoreDesc();

		// 랭크 세기
		for (Baekjoon baekjoon : baekjoonList) {
			if (baekjoon.getUser().getId() == userId) {
				BojRankResponseDTO bojRankResponseDTO = BojRankResponseDTO.createBojMyRankResponseDTO(baekjoon,
					user, rank);
				return bojRankResponseDTO;
			} else {
				rank++;
			}
		}
		//유저 아아디에는 백준이 있는데 조회가 안된다면 에러
		throw new CustomException(NOT_FOUND_BOJ_USER);

	}

	/**
	 * 주어진 백준 ID(bojId)를 가진 사용자 목록을 조회하고,
	 * 각 사용자에 대한 BojIdListResponseDTO 객체를 생성하여 이들을 리스트로 반환하는 메소드입니다.
	 *
	 * @param nickname 조회할 사용자의 백준 ID
	 * @author noobsoda
	 * @return NicknameListResponseDTO 객체 목록
	 */
	public List<NicknameListResponseDTO> getBojListByBojId(String nickname) {
		List<User> userList = userQueryRepository.findByBojId(nickname);
		return userList.stream()
			.map(u -> NicknameListResponseDTO.create(u.getId(), u.getBojId()))
			.collect(Collectors.toList());
	}

	/**
	 *
	 @param userId 유저의 아이디
	 @author noobsoda
	 @return BojInfoDetailResponseDTO 백준 정보 상세를 담은 응답 DTO
	 */
	@Override
	public BojInfoDetailResponseDTO getBojInfoDetailByUserId(Long userId) {
		//언어 정보를 저장할 해쉬맵
		HashMap<Long, String> languageMap = new HashMap<>();

		//유저 아이디로 백준 아이디 조회
		Optional<User> oUser = userRepository.findById(userId);
		User user = oUser.orElseThrow(() -> new CustomException(NOT_FOUND_USER));

		// 백준 아이디가 없는 유저입니다.
		Optional<Baekjoon> oBaekjoon = bojRepository.findByUserId(userId);
		Baekjoon baekjoon = oBaekjoon.orElseThrow(() -> new CustomException(NOT_FOUND_BOJ_USER));

		List<BaekjoonLanguage> baekjoonLanguageList = bojLanguageRepository.findAllById(baekjoon.getId());
		//언어 정보 불러와서 해쉬에 저장
		List<Language> languageList = languageRepository.findAllByType(LanguageType.BAEKJOON);
		for (Language language : languageList) {
			languageMap.put(language.getId(), language.getName());
		}

		//언어 정보
		List<BojLanguageDTO> bojLanguageDTOList = baekjoonLanguageList.stream()
			.map(u -> BojLanguageDTO.create(languageMap.get(u.getLanguageId()), u.getPassPercentage()))
			.collect(Collectors.toList());
		BojInfoDetailResponseDTO bojInfoDetailResponseDTO = BojInfoDetailResponseDTO.create(user, baekjoon,
			bojLanguageDTOList);

		return bojInfoDetailResponseDTO;
	}

}
