package com.ssafy.backend.domain.analysis.service;

import static com.ssafy.backend.global.response.exception.CustomExceptionStatus.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.backend.domain.algorithm.dto.response.BojInfoDetailResponse;
import com.ssafy.backend.domain.algorithm.dto.response.BojLanguageResponse;
import com.ssafy.backend.domain.algorithm.repository.BojLanguageRepository;
import com.ssafy.backend.domain.algorithm.repository.BojRepository;
import com.ssafy.backend.domain.analysis.dto.BojRankComparison;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalysisBojService {
	private final UserRepository userRepository;
	private final BojRepository bojRepository;
	private final BojLanguageRepository bojLanguageRepository;
	private final LanguageRepository languageRepository;

	public BojRankComparison compareWithOpponent(long id, Long userId) {
		//언어 정보를 저장할 해쉬맵
		HashMap<Long, String> languageMap = new HashMap<>();

		User myUser = userRepository.findById(id).orElseThrow(() -> new CustomException(NOT_FOUND_USER));
		User opponentUser = userRepository.findById(userId).orElseThrow(() -> new CustomException(NOT_FOUND_USER));

		//둘 중 한명이라도 백준 아이디가 비어있다면
		if (myUser.getBojId() == null || opponentUser.getBojId() == null) {
			return BojRankComparison.createEmpty();
		}
		//둘 중 한명이라도 백준 아이디가 잘못되있다면
		Baekjoon myBaekjoon = bojRepository.findByUser(myUser)
			.orElseThrow(() -> new CustomException(NOT_FOUND_BOJ_USER));
		Baekjoon opponentBaekjoon = bojRepository.findByUser(opponentUser)
			.orElseThrow(() -> new CustomException(NOT_FOUND_BOJ_USER));

		//언어 정보 불러와서 해쉬에 저장
		List<Language> languageList = languageRepository.findAllByType(LanguageType.BAEKJOON);
		for (Language language : languageList) {
			languageMap.put(language.getId(), language.getName());
		}

		List<BojLanguageResponse> myBojLanguageList = createBojLanguageList(myBaekjoon.getId(), languageMap);
		List<BojLanguageResponse> opponentBojLanguageList = createBojLanguageList(opponentBaekjoon.getId(),
			languageMap);

		return BojRankComparison.create(BojInfoDetailResponse.create(myUser, myBaekjoon, myBojLanguageList),
			BojInfoDetailResponse.create(opponentUser, opponentBaekjoon, opponentBojLanguageList));
	}

	private List<BojLanguageResponse> createBojLanguageList(long bojId, HashMap<Long, String> languageMap) {
		List<BaekjoonLanguage> bojList = bojLanguageRepository.findAllByBaekjoonId(bojId);

		return bojList.stream()
			.map(u -> BojLanguageResponse.create(languageMap.get(u.getLanguageId()), u.getPassPercentage(),
				u.getPassCount()))
			.collect(Collectors.toList());

	}
}
