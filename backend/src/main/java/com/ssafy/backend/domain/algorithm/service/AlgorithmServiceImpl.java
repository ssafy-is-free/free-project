package com.ssafy.backend.domain.algorithm.service;

import static com.ssafy.backend.global.response.exception.CustomExceptionStatus.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.ssafy.backend.domain.algorithm.dto.response.BojInfoDetailResponse;
import com.ssafy.backend.domain.algorithm.dto.response.BojLanguageResponse;
import com.ssafy.backend.domain.algorithm.dto.response.BojRankResponse;
import com.ssafy.backend.domain.algorithm.repository.BojLanguageRepository;
import com.ssafy.backend.domain.algorithm.repository.BojRepository;
import com.ssafy.backend.domain.algorithm.repository.BojRepositorySupport;
import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.BaekjoonLanguage;
import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.entity.common.LanguageType;
import com.ssafy.backend.domain.user.dto.NicknameListResponse;
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
		return;
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
	public BojRankResponse getBojByUserId(long userId) {
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
				BojRankResponse bojRankResponse = BojRankResponse.createBojMyRankResponseDTO(baekjoon,
					user, rank);
				return bojRankResponse;
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
	public List<NicknameListResponse> getBojListByBojId(String nickname) {
		List<User> userList = userQueryRepository.findByBojId(nickname);
		return userList.stream()
			.map(u -> NicknameListResponse.create(u.getId(), u.getBojId()))
			.collect(Collectors.toList());
	}

	/**
	 *
	 @param userId 유저의 아이디
	 @author noobsoda
	 @return BojInfoDetailResponseDTO 백준 정보 상세를 담은 응답 DTO
	 */
	@Override
	public BojInfoDetailResponse getBojInfoDetailByUserId(Long userId) {
		//언어 정보를 저장할 해쉬맵
		HashMap<Long, String> languageMap = new HashMap<>();

		//유저 아이디로 백준 아이디 조회
		Optional<User> oUser = userRepository.findById(userId);
		User user = oUser.orElseThrow(() -> new CustomException(NOT_FOUND_USER));

		// 백준 아이디가 없는 유저입니다.
		Optional<Baekjoon> oBaekjoon = bojRepository.findByUserId(userId);
		Baekjoon baekjoon = oBaekjoon.orElseThrow(() -> new CustomException(NOT_FOUND_BOJ_USER));

		List<BaekjoonLanguage> baekjoonLanguageList = bojLanguageRepository.findAllByBaekjoonId(baekjoon.getId());
		//언어 정보 불러와서 해쉬에 저장
		List<Language> languageList = languageRepository.findAllByType(LanguageType.BAEKJOON);
		for (Language language : languageList) {
			languageMap.put(language.getId(), language.getName());
		}

		//언어 정보
		List<BojLanguageResponse> bojLanguageDTOList = baekjoonLanguageList.stream()
			.map(u -> BojLanguageResponse.create(languageMap.get(u.getLanguageId()), u.getPassPercentage()))
			.collect(Collectors.toList());
		BojInfoDetailResponse bojInfoDetailResponse = BojInfoDetailResponse.create(user, baekjoon,
			bojLanguageDTOList);

		return bojInfoDetailResponse;
	}

}
