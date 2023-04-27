package com.ssafy.backend.domain.github.service;

import static com.ssafy.backend.global.response.exception.CustomExceptionStatus.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.ssafy.backend.domain.github.dto.CGithubRepoDTO;
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

	public void getGithubInfo(String nickname, long userId) {
		User findUser = userRepository.findByIdAndIsDeletedFalse(userId).orElseThrow(
			() -> new CustomException(NOT_FOUND_USER)
		);

		// TODO: 2023-04-24 크롤링 속도 개선하기
		CGithubDTO githubCrawling = webClient.get()
			.uri("/data/github/" + nickname)
			.retrieve()
			.bodyToMono(CGithubDTO.class)
			.block();

		// TODO: 2023-04-24 findByUserId 대신에 repo, language 조인한거로 바꾸기
		Optional<Github> githubOptional = githubRepository.findByUserId(findUser.getId());
		if (githubOptional.isPresent()) {   // 기존 유저
			Github github = githubOptional.get();

			int score = Github.calcScore(githubCrawling.getCommit(), githubCrawling.getFollowers(),
				githubCrawling.getStar(), githubCrawling.getRepositories().size());


			github.update(githubCrawling.getCommit(), githubCrawling.getFollowers(),
				githubCrawling.getStar(), githubCrawling.getProfileLink(), score);

			List<GithubRepo> githubRepos = new ArrayList<>();
			for (CGithubRepoDTO g : githubCrawling.getRepositories()) {
				githubRepos.add(GithubRepo.create(g.getName(), g.getReadme(), g.getLink(), github));
			}

			// TODO: 2023-04-24 save 횟수 및 쿼리 횟수 줄이기
			github.getGithubRepos().addAll(githubRepos);    // 기존 깃허브 레포와 크롤링한 레포 합집합
			for (GithubRepo githubRepo : github.getGithubRepos()) {
				githubRepoRepository.save(githubRepo);
			}

			List<GithubLanguage> githubLanguages = new ArrayList<>();
			for (CGithubLanguageDTO l : githubCrawling.getLanguages()) {
				Language findLanguage = languageRepository.findByNameAndType(l.getName(), LanguageType.GITHUB)
					.orElseGet(
						() -> {
							Language language = Language.create(l.getName(), LanguageType.GITHUB);
							return languageRepository.save(language);
						}
					);
				githubLanguages.add(GithubLanguage.create(findLanguage.getId(), l.getPercentage(), github));
			}

			// TODO: 2023-04-24 save 횟수 및 쿼리 횟수 줄이기
			github.getGithubLanguages().addAll(githubLanguages);    // 기존 깃허브 언어와 크롤링한 언어 합집합
			for (GithubLanguage githubLanguage : github.getGithubLanguages()) {
				githubLanguageRepository.save(githubLanguage);
			}

		} else {    // 새로운 유저
			int score = Github.calcScore(githubCrawling.getCommit(), githubCrawling.getFollowers(),
				githubCrawling.getStar(), githubCrawling.getRepositories().size());

			Github github = Github.create(
				githubCrawling.getCommit(), githubCrawling.getFollowers(),
				githubCrawling.getStar(), githubCrawling.getProfileLink(), findUser, score
			);

			githubRepository.save(github);

			// TODO: 2023-04-24 save 횟수 및 쿼리 횟수 줄이기
			githubCrawling.getRepositories().stream().map(
				g -> GithubRepo.create(g.getName(), g.getReadme(), g.getLink(), github)
			).forEach(
				g -> githubRepoRepository.save(g)
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
			).forEach(
				l -> githubLanguageRepository.save(l)
			);
		}
	}
}
