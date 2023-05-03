package com.ssafy.backend.domain.analysis.service;

import static com.ssafy.backend.global.response.exception.CustomExceptionStatus.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.backend.domain.algorithm.dto.response.BojInfoDetailResponse;
import com.ssafy.backend.domain.algorithm.dto.response.BojLanguageResponse;
import com.ssafy.backend.domain.algorithm.repository.BojLanguageRepository;
import com.ssafy.backend.domain.algorithm.repository.BojQueryRepository;
import com.ssafy.backend.domain.algorithm.repository.BojRepository;
import com.ssafy.backend.domain.analysis.dto.BojInfoAvgDetailResponse;
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
	private final BojQueryRepository bojQueryRepository;

	public BojRankComparison compareWithOpponent(long id, Long userId) {
		//언어 정보를 저장할 해쉬맵
		Map<Long, String> languageMap = getLanguageMap();

		//백준 디테일 정보 저장
		BojInfoDetailResponse my = createBojInfoDetail(id, languageMap);
		BojInfoDetailResponse opp = createBojInfoDetail(userId, languageMap);

		return BojRankComparison.create(my, opp);

	}

	public BojRankComparison compareWithOther(long id, Long jobPostingId) {
		//언어 정보를 저장할 해쉬맵
		Map<Long, String> languageMap = getLanguageMap();
		//백준 디테일 정보 저장
		BojInfoDetailResponse my = createBojInfoDetail(id, languageMap);

		//백준 전체 정보 저장
		BojInfoAvgDetailResponse other = null;
		//passCount top 5 And passCount / 인원 수 보내기

		return null;
	}

	private Map<Long, String> getLanguageMap() {
		Map<Long, String> languageMap = new HashMap<>();
		//언어 정보 불러와서 해쉬에 저장
		List<Language> languageList = languageRepository.findAllByType(LanguageType.BAEKJOON);
		for (Language language : languageList) {
			languageMap.put(language.getId(), language.getName());
		}
		return languageMap;
	}

	private BojInfoDetailResponse createBojInfoDetail(long userId, Map<Long, String> languageMap) {
		User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(NOT_FOUND_USER));
		//백준 아이디가 비어있다면
		if (user.getBojId() == null) {
			return BojInfoDetailResponse.createEmpty();
		}
		Baekjoon baekjoon = bojRepository.findByUser(user)
			.orElseThrow(() -> new CustomException(NOT_FOUND_BOJ_USER));

		List<BaekjoonLanguage> bojList = bojLanguageRepository.findAllByBaekjoonId(baekjoon.getId());

		List<BojLanguageResponse> bojLanguageList = bojList.stream()
			.map(u -> BojLanguageResponse.create(languageMap.get(u.getLanguageId()), u.getPassPercentage(),
				u.getPassCount()))
			.collect(Collectors.toList());

		return BojInfoDetailResponse.create(user, baekjoon, bojLanguageList);
	}

	private List<BojLanguageResponse> createBojLanguageList(long bojId, HashMap<Long, String> languageMap) {
		List<BaekjoonLanguage> bojList = bojLanguageRepository.findAllByBaekjoonId(bojId);

		return bojList.stream()
			.map(u -> BojLanguageResponse.create(languageMap.get(u.getLanguageId()), u.getPassPercentage(),
				u.getPassCount()))
			.collect(Collectors.toList());

	}
}
