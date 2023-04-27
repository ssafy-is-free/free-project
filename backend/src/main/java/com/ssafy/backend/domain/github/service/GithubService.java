package com.ssafy.backend.domain.github.service;

import static com.ssafy.backend.global.response.exception.CustomExceptionStatus.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.GithubRepo;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.github.dto.GithubDetailLanguage;
import com.ssafy.backend.domain.github.dto.GithubDetailResponse;
import com.ssafy.backend.domain.github.dto.ReadmeResponse;
import com.ssafy.backend.domain.github.repository.GithubRepoRepository;
import com.ssafy.backend.domain.github.repository.GithubRepository;
import com.ssafy.backend.domain.github.repository.querydsl.GithubLanguageQueryRepository;
import com.ssafy.backend.domain.user.dto.NicknameListResponse;
import com.ssafy.backend.domain.user.repository.UserQueryRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.global.response.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubService {
	private final GithubRepoRepository githubRepoRepository;
	private final GithubLanguageQueryRepository githubLanguageQueryRepository;
	private final GithubRepository githubRepository;
	private final UserQueryRepository userQueryRepository;
	private final UserRepository userRepository;

	public List<NicknameListResponse> getNicknameList(String nickname) {
		List<User> userList = userQueryRepository.findByNickname(nickname);
		return userList.stream()
			.map(u -> NicknameListResponse.create(u.getId(), u.getNickname()))
			.collect(Collectors.toList());
	}

	@Transactional
	public GithubDetailResponse getDetails(long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(NOT_FOUND_USER));
		Github github = githubRepository.findByUser(user).orElseThrow(() -> new CustomException(NOT_FOUND_GITHUB));

		List<GithubDetailLanguage> githubLanguages = githubLanguageQueryRepository.findByGithub(github);
		log.info(github.toString());
		return GithubDetailResponse.create(github, githubLanguages);
	}

	public ReadmeResponse getReadme(long githubId, long repositoryId) {
		GithubRepo githubRepo = githubRepoRepository.findById(repositoryId).orElseThrow(() -> {
			throw new CustomException(NOT_FOUND_REPOSITORY);
		});

		githubRepo.validateGithubId(githubId);
		return ReadmeResponse.create(githubRepo.getReadme());
	}

}