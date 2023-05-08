package com.ssafy.backend.domain.analysis.service;

import static com.ssafy.backend.global.response.exception.CustomExceptionStatus.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.backend.domain.algorithm.dto.response.BojInfoDetailResponse;
import com.ssafy.backend.domain.algorithm.dto.response.BojLanguageResponse;
import com.ssafy.backend.domain.algorithm.repository.BojLanguageQueryRepository;
import com.ssafy.backend.domain.algorithm.repository.BojLanguageRepository;
import com.ssafy.backend.domain.algorithm.repository.BojQueryRepository;
import com.ssafy.backend.domain.algorithm.repository.BojRepository;
import com.ssafy.backend.domain.analysis.dto.BojAvgDetail;
import com.ssafy.backend.domain.analysis.dto.BojLanguagePassCount;
import com.ssafy.backend.domain.analysis.dto.BojRankAllComparisonResponse;
import com.ssafy.backend.domain.analysis.dto.BojRankComparisonResponse;
import com.ssafy.backend.domain.analysis.dto.response.BojInfoAvgDetailResponse;
import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.BaekjoonLanguage;
import com.ssafy.backend.domain.entity.JobHistory;
import com.ssafy.backend.domain.entity.JobPosting;
import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.entity.common.LanguageType;
import com.ssafy.backend.domain.github.dto.FilteredUserIdSet;
import com.ssafy.backend.domain.job.repository.JobHistoryRepository;
import com.ssafy.backend.domain.job.repository.JobPostingRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.util.repository.LanguageRepository;
import com.ssafy.backend.global.response.exception.CustomException;
import com.ssafy.backend.global.response.exception.CustomExceptionStatus;

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
	private final BojLanguageQueryRepository bojLanguageQueryRepository;
	private final JobPostingRepository jobPostingRepository;
	private final JobHistoryRepository jobHistoryRepository;

	public BojRankComparisonResponse compareWithOpponent(long id, Long userId) {
		//언어 정보를 저장할 해쉬맵
		Map<Long, String> languageMap = getLanguageMap();

		//백준 디테일 정보 저장
		BojInfoDetailResponse my = createBojInfoDetail(id, languageMap);
		BojInfoDetailResponse opp = createBojInfoDetail(userId, languageMap);

		return BojRankComparisonResponse.create(my, opp);

	}

	public BojRankAllComparisonResponse compareWithOther(long id, Long jobPostingId) {
		//언어 정보를 저장할 해쉬맵
		Map<Long, String> languageMap = getLanguageMap();
		//백준 디테일 정보 저장
		BojInfoDetailResponse my = createBojInfoDetail(id, languageMap);

		//공고별로 필터링된 userIds
		FilteredUserIdSet userIdSet = (jobPostingId == null) ? null : getUserIdByJobPosting(jobPostingId);

		//공고별 전체 평균값 구하기
		BojAvgDetail bojAvgDetail = bojQueryRepository.findByAvg(userIdSet);
		//값 포맷팅
		bojAvgDetail.roundToDecimalPlace();

		//언어별 pass 최상위 5가지
		List<BojLanguagePassCount> bojLanguagePassCount = bojLanguageQueryRepository.findBojLanguagePassCount();
		List<BojLanguageResponse> bojLanguageList = bojLanguagePassCount.stream()
			.map(u -> BojLanguageResponse.create(languageMap.get(u.getLanguageId()),
				null, u.getTotalPassCount()))
			.collect(Collectors.toList());

		//백준 전체 정보 저장
		BojInfoAvgDetailResponse other = BojInfoAvgDetailResponse.create(bojAvgDetail, bojLanguageList);

		return BojRankAllComparisonResponse.builder().my(my).other(other).build();
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

	private FilteredUserIdSet getUserIdByJobPosting(Long jobPostingId) {
		//공고 유효성 검증
		JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_JOBPOSTING));

		List<JobHistory> jobHistoryList = jobHistoryRepository.findByJobPosting(jobPosting);
		return FilteredUserIdSet.create(jobHistoryList);
	}
}