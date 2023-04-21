package com.ssafy.backend.domain.github.service;

import com.ssafy.backend.domain.entity.*;
import com.ssafy.backend.domain.entity.common.LanguageType;
import com.ssafy.backend.domain.github.dto.CGithubDTO;
import com.ssafy.backend.domain.github.repository.GithubLanguageRepository;
import com.ssafy.backend.domain.github.repository.GithubRepoRepository;
import com.ssafy.backend.domain.github.repository.GithubRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.util.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


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

    public void get(String nickname, long userId) {
        User findUser = userRepository.findById(userId).orElseThrow();

        CGithubDTO githubCrawling = webClient.get()
                .uri("/api/data/github/" + nickname)
                .retrieve()
                .bodyToMono(CGithubDTO.class)
                .block();

        Github github = Github.create(
                githubCrawling.getCommit(), githubCrawling.getFollowers(),
                githubCrawling.getStar(), githubCrawling.getProfileLink(), findUser
        );

        githubRepository.save(github);

        githubCrawling.getRepositories().stream().map(
                g -> GithubRepo.create(g.getName(), g.getReadme(), g.getLink(), github)
        ).forEach(
                g -> githubRepoRepository.save(g)
        );

        githubCrawling.getLanguages().stream().map(
                l -> {
                    Language findLanguage = languageRepository.findByNameAndType(l.getName(), LanguageType.GITHUB).orElseGet(
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
