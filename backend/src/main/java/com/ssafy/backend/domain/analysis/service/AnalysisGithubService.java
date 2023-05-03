package com.ssafy.backend.domain.analysis.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.backend.domain.analysis.dto.response.CompareGithubResponse;
import com.ssafy.backend.domain.analysis.dto.response.GithubVsInfo;
import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.github.dto.GithubDetailLanguage;
import com.ssafy.backend.domain.github.repository.GithubRepository;
import com.ssafy.backend.domain.github.repository.querydsl.GithubLanguageQueryRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.global.response.exception.CustomException;
import com.ssafy.backend.global.response.exception.CustomExceptionStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnalysisGithubService {
	private final GithubRepository githubRepository;
	private final UserRepository userRepository;
	private final GithubLanguageQueryRepository githubLanguageQueryRepository;

	public CompareGithubResponse compareWithOpponent(long opponentUserId, long myUserId) {
		//나의 정보
		GithubVsInfo myVsInfo = getGithubVsInfo(myUserId);

		//상대방 정보
		GithubVsInfo opponentVsInfo = getGithubVsInfo(opponentUserId);

		return CompareGithubResponse.create(myVsInfo, opponentVsInfo);
	}

	private GithubVsInfo getGithubVsInfo(long myUserId) {
		User user = userRepository.findById(myUserId)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_USER));

		Github github = githubRepository.findByUser(user)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_GITHUB));

		//언어 정보
		List<GithubDetailLanguage> languageList = getDetailLanguages(github);
		return GithubVsInfo.crate(github, languageList);
	}

	private List<GithubDetailLanguage> getDetailLanguages(Github github) {
		return githubLanguageQueryRepository.findByGithub(github)
			.stream()
			.sorted(Comparator.comparingDouble(l -> -Double.parseDouble(l.getPercentage())))
			.collect(Collectors.toList());

	}

}
