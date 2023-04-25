package com.ssafy.backend.domain.github.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.github.dto.GitHubRankingFilter;
import com.ssafy.backend.domain.github.dto.GithubRankingCover;
import com.ssafy.backend.domain.github.dto.GithubRankingResponse;
import com.ssafy.backend.domain.github.dto.LanguageCond;
import com.ssafy.backend.domain.github.repository.GithubLanguageRepository;
import com.ssafy.backend.domain.github.repository.GithubRepository;
import com.ssafy.backend.domain.user.dto.NicknameListResponseDTO;
import com.ssafy.backend.domain.user.repository.UserQueryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubService {
	private final GithubLanguageRepository githubLanguageRepository;
	private final GithubRepository githubRepository;
	private final UserQueryRepository userQueryRepository;

	public GithubRankingResponse getGithubRank(long rank, Long githubId, Integer score, Pageable pageable) {
		List<GithubRankingCover> githubCovers = githubRepository.findAll(githubId, score, pageable)
			.stream()
			.map(GithubRankingCover::new)
			.collect(Collectors.toList());

		setRankToResult(rank, githubCovers);

		log.info(githubCovers.toString());
		return new GithubRankingResponse(githubCovers);
	}

	//랭킹 반영
	private static void setRankToResult(long rank, List<GithubRankingCover> rankingCovers) {
		for (GithubRankingCover rankingCover : rankingCovers) {
			rankingCover.setRank(rank);
			rankingCover.setRankUpDown(rankingCover.getPrevRank() - rank);
			rank++;
		}
	}

	public GithubRankingResponse getGithubRank(GitHubRankingFilter rankingFilter, Pageable pageable) {
		LanguageCond languageCond = new LanguageCond(rankingFilter.getLanguage());

		Set<Long> githubIdByLanguage = getGithubIdByLanguage(languageCond);

		log.info(githubIdByLanguage.toString());

		return null;
	}

	//해당 언어를 사용하는 깃허브 아이디
	private Set<Long> getGithubIdByLanguage(LanguageCond languageCond) {
		return githubLanguageRepository.findByLanguageIdIn(languageCond.getLanguages())
			.stream()
			.map(g -> g.getGithub().getId())
			.collect(Collectors.toSet());
	}

	public List<NicknameListResponseDTO> getNicknameList(String nickname) {
		List<User> userList = userQueryRepository.findByNickname(nickname);
		return userList.stream().map(
			u -> NicknameListResponseDTO.create(u.getId(), u.getNickname())
		).collect(Collectors.toList());
	}

}