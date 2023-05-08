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

		Optional<Github> githubOptional = githubRepository.findByUserId(findUser.getId());
		if (githubOptional.isPresent()) {   // 기존 유저
			Github github = githubOptional.get();

			github.update(githubCrawling);

			// 새로 가져온 레포들의 이름 리스트
			List<String> newRepoNameList = githubCrawling.getRepositories().stream()
				.map(r -> r.getName())
				.collect(Collectors.toList());

			// 기존 레포들의 이름 리스트
			List<String> oldRepoNameList = github.getGithubRepos().stream()
				.map(r -> r.getName())
				.collect(Collectors.toList());

			// 새로 가져온 레포에 없는 경우 -> 레포지토리 삭제
			github.getGithubRepos().stream()
				.filter(r -> !newRepoNameList.contains(r.getName()))
				.forEach(r -> github.getGithubRepos().remove(r));

			// 새로운 레포지토리 추가
			githubCrawling.getRepositories().stream()
				.filter(r -> !oldRepoNameList.contains(r.getName()))
				.map(r -> GithubRepo.create(r.getName(), r.getReadme(), r.getLink(), github))
				.forEach(r -> github.getGithubRepos().add(r));


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

			// 기존에 있던 언어 PK 리스트
			List<Long> oldLanguageList = github.getGithubLanguages().stream()
				.map(l -> l.getLanguageId())
				.collect(Collectors.toList());

			// 새로운 언어 추가
			newGithubLanguageList.stream()
				.filter(l -> !oldLanguageList.contains(l.getLanguageId()))
				.forEach(l -> github.getGithubLanguages().add(l));

			// 기존에 있던 언어 정보 업데이트
			Set<GithubLanguage> githubLanguages = github.getGithubLanguages();
			for (GithubLanguage githubLanguage : githubLanguages) {
				for (GithubLanguage language : newGithubLanguageList) {
					if (githubLanguage.getLanguageId() == language.getLanguageId()) {
						githubLanguage.update(language.getPercentage());
					}
				}
			}
		} else {    // 새로운 유저
			Github github = Github.create(githubCrawling, findUser);

			githubRepository.save(github);

			// TODO: 2023-04-24 save 횟수 및 쿼리 횟수 줄이기
			githubCrawling.getRepositories().stream().map(
				g -> GithubRepo.create(g.getName(), g.getReadme(), g.getLink(), github)
			);

			// TODO: 2023-04-24 save 횟수 및 쿼리 횟수 줄이기
			githubCrawling.getLanguages().stream().map(
				l -> {
					Language findLanguage = languageRepository.findByNameAndType(l.getName(), LanguageType.GITHUB)
						.orElseGet(
							() -> {
								Language language = Language.create(l.getName(), LanguageType.GITHUB);
								return languageRepository.save(language);
							}
						);
					return GithubLanguage.create(findLanguage.getId(), l.getPercentage(), github);
				}
			);
		}
	}

	public void updateAllGithub(String nickname, long userId) {
		CGithubDTO githubCrawling = webClient.get()
			.uri("/data/github/update/" + nickname)
			.retrieve()
			.bodyToMono(CGithubDTO.class)
			.block();

		Optional<Github> githubOptional = githubRepository.findByUserId(userId);
		if (githubOptional.isPresent()) {   // 기존 유저
			Github github = githubOptional.get();

			github.update(githubCrawling);

			// 새로 가져온 레포들의 이름 리스트
			List<String> newRepoNameList = githubCrawling.getRepositories().stream()
				.map(r -> r.getName())
				.collect(Collectors.toList());

			// 기존 레포들의 이름 리스트
			List<String> oldRepoNameList = github.getGithubRepos().stream()
				.map(r -> r.getName())
				.collect(Collectors.toList());

			// 새로 가져온 레포에 없는 경우 -> 레포지토리 삭제
			github.getGithubRepos().stream()
				.filter(r -> !newRepoNameList.contains(r.getName()))
				.forEach(r -> github.getGithubRepos().remove(r));

			// 새로운 레포지토리 추가
			githubCrawling.getRepositories().stream()
				.filter(r -> !oldRepoNameList.contains(r.getName()))
				.map(r -> GithubRepo.create(r.getName(), r.getReadme(), r.getLink(), github))
				.forEach(r -> github.getGithubRepos().add(r));

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

			// 기존에 있던 언어 PK 리스트
			List<Long> oldLanguageList = github.getGithubLanguages().stream()
				.map(l -> l.getLanguageId())
				.collect(Collectors.toList());

			// 새로운 언어 추가
			newGithubLanguageList.stream()
				.filter(l -> !oldLanguageList.contains(l.getLanguageId()))
				.forEach(l -> github.getGithubLanguages().add(l));

			// 기존에 있던 언어 정보 업데이트
			Set<GithubLanguage> githubLanguages = github.getGithubLanguages();
			for (GithubLanguage githubLanguage : githubLanguages) {
				for (GithubLanguage language : newGithubLanguageList) {
					if (githubLanguage.getLanguageId() == language.getLanguageId()) {
						githubLanguage.update(language.getPercentage());
					}
				}
			}
		}
	}
}
