package com.ssafy.backend.domain.github.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.backend.domain.github.dto.GitHubRankingFilter;
import com.ssafy.backend.domain.github.dto.GithubRankingResponse;
import com.ssafy.backend.domain.github.dto.LanguageCond;
import com.ssafy.backend.domain.github.repository.GithubLanguageRepository;
import com.ssafy.backend.domain.github.repository.projection.GithubId;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class GithubService {
	private final GithubLanguageRepository githubLanguageRepository;

	public GithubRankingResponse getGithubRank(GitHubRankingFilter rankingFilter, Pageable pageable) {
		LanguageCond languageCond = new LanguageCond(rankingFilter.getLanguage());

		List<Long> githubIdByLanguage = getGithubIdByLanguage(languageCond);
		log.info(githubIdByLanguage.toString());

		return null;
	}

	private List<Long> getGithubIdByLanguage(LanguageCond languageCond) {
		return githubLanguageRepository.findByLanguageIdIn(languageCond.getLanguages())
			.stream()
			.map(GithubId::getGithubId)
			.collect(
				Collectors.toList());
		//.map(g -> g.getGithub_id())
	}

}
