package com.ssafy.backend.domain.github.service;

import static com.ssafy.backend.global.response.exception.CustomExceptionStatus.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.GithubLanguage;
import com.ssafy.backend.domain.entity.GithubRepo;
import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.entity.common.LanguageType;
import com.ssafy.backend.domain.github.dto.CGithubDTO;
import com.ssafy.backend.domain.github.dto.CGithubLanguageDTO;
import com.ssafy.backend.domain.github.repository.GithubLanguageRepository;
import com.ssafy.backend.domain.github.repository.GithubRepoRepository;
import com.ssafy.backend.domain.github.repository.GithubRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.util.repository.LanguageRepository;
import com.ssafy.backend.global.response.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GithubCrawlingService {

	private final UserRepository userRepository;

	private final LanguageRepository languageRepository;

	private final GithubRepository githubRepository;
	private final GithubLanguageRepository githubLanguageRepository;
	private final GithubRepoRepository githubRepoRepository;

	private final WebClient webClient;

	public void getGithubInfo(String token, long userId) {
		User findUser = userRepository.findByIdAndIsDeletedFalse(userId).orElseThrow(
			() -> new CustomException(NOT_FOUND_USER)
		);

		CGithubDTO githubCrawling = webClient.get()
			.uri("/data/github/" + token)
			.retrieve()
			.bodyToMono(CGithubDTO.class)
			.block();

		// 토큰에 해당하는 깃허브 유저 정보가 없는경우
		if (githubCrawling.getNickname().equals("")) {
			throw new CustomException(NOT_FOUND_GITHUB);
		}

		Optional<Github> githubOptional = githubRepository.findByUserId(findUser.getId());
		if (githubOptional.isPresent()) {   // 기존 유저
			Github github = githubOptional.get();

			// 새로 가져온 언어 정보
			List<GithubLanguage> newGithubLanguageList = new ArrayList<>();
			for (CGithubLanguageDTO l : githubCrawling.getLanguages()) {
				Language findLanguage = languageRepository.findByNameAndType(l.getName(), LanguageType.GITHUB)
					.orElseGet(
						() -> {
							Language language = Language.create(l.getName(), LanguageType.GITHUB);
							return languageRepository.save(language);
						}
					);
				newGithubLanguageList.add(GithubLanguage.create(findLanguage.getId(), l.getPercentage(), github));
			}

			github.update(githubCrawling, newGithubLanguageList);

		} else {    // 새로운 유저
			Github github = Github.create(githubCrawling, findUser);

			// TODO: 2023-04-24 save 횟수 및 쿼리 횟수 줄이기
			githubCrawling.getRepositories().stream().
				map(g -> GithubRepo.create(g.getName(), g.getReadme(), g.getLink(), github))
				.forEach(gr -> github.getGithubRepos().add(gr));

			// TODO: 2023-04-24 save 횟수 및 쿼리 횟수 줄이기
			githubCrawling.getLanguages().stream()
				.map(
					l -> {
						Language findLanguage = languageRepository.findByNameAndType(l.getName(), LanguageType.GITHUB)
							.orElseGet(
								() -> {
									Language language = Language.create(l.getName(), LanguageType.GITHUB);
									return languageRepository.save(language);
								}
							);
						return GithubLanguage.create(findLanguage.getId(), l.getPercentage(), github);
					})
				.forEach(gl -> github.getGithubLanguages().add(gl));

			githubRepository.save(github);
		}
	}

	public void updateAllGithub(String nickname, long userId) {

		// 유저아이디로 깃허브 정보 조회
		Github github = githubRepository.findByUserId(userId).orElseThrow(
			() -> new CustomException(NOT_FOUND_GITHUB)
		);

		CGithubDTO githubCrawling = webClient.get()
			.uri("/data/github/update/" + nickname)
			.retrieve()
			.bodyToMono(CGithubDTO.class)
			.block();

		// 닉네임에 해당하는 깃허브 유저 정보가 없는경우
		if (githubCrawling.getNickname().equals("")) {
			throw new CustomException(NOT_FOUND_GITHUB);
		}

		// 새로 가져온 언어 정보
		List<GithubLanguage> newGithubLanguageList = new ArrayList<>();
		for (CGithubLanguageDTO l : githubCrawling.getLanguages()) {
			Language findLanguage = languageRepository.findByNameAndType(l.getName(), LanguageType.GITHUB)
				.orElseGet(
					() -> {
						Language language = Language.create(l.getName(), LanguageType.GITHUB);
						return languageRepository.save(language);
					}
				);
			newGithubLanguageList.add(GithubLanguage.create(findLanguage.getId(), l.getPercentage(), github));
		}

		// 깃허브 정보 업데이트
		github.update(githubCrawling, newGithubLanguageList);
	}
}
