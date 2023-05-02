package com.ssafy.backend.domain.analysis.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.backend.domain.analysis.dto.response.CompareGithubResponse;
import com.ssafy.backend.domain.analysis.dto.response.GithubVsInfo;
import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.github.repository.GithubRepository;
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

	public CompareGithubResponse compareWithOpponent(long opponentUserId, long myUserId) {
		//나의 정보
		User me = userRepository.findById(myUserId)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_USER));

		Github myGithub = githubRepository.findByUser(me)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_GITHUB));

		GithubVsInfo myVsInfo = GithubVsInfo.crate(myGithub);

		//상대방 정보
		User opponent = userRepository.findById(opponentUserId)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_USER));

		Github opponentGithub = githubRepository.findByUser(opponent)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_GITHUB));

		GithubVsInfo opponentVsInfo = GithubVsInfo.crate(opponentGithub);

		return CompareGithubResponse.create(myVsInfo, opponentVsInfo);
	}

}
