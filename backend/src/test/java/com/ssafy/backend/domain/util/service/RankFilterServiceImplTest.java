package com.ssafy.backend.domain.util.service;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.common.LanguageType;
import com.ssafy.backend.domain.util.dto.LanguageResponse;
import com.ssafy.backend.domain.util.repository.LanguageQueryRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("랭킹 필터 서비스 테스트")
class RankFilterServiceImplTest {

	@Mock
	private LanguageQueryRepository languageQueryRepository;

	private RankFilterServiceImpl rankFilterService;

	@BeforeEach
	void setUp() {
		this.rankFilterService = new RankFilterServiceImpl(languageQueryRepository);
	}

	@Test
	@DisplayName("언어 리스트 조회 테스트")
	public void testGetLanguageList() {

		//given
		List<Language> languageGihubList = new ArrayList<>();
		languageGihubList.add(Language.create("C++", LanguageType.valueOf("GITHUB")));
		languageGihubList.add(Language.create("C", LanguageType.valueOf("GITHUB")));
		languageGihubList.add(Language.create("JAVA", LanguageType.valueOf("GITHUB")));
		languageGihubList.add(Language.create("PYTHON", LanguageType.valueOf("GITHUB")));
		when(languageQueryRepository.findLanguageByType("GITHUB")).thenReturn(languageGihubList);

		List<Language> languageBaekJoonList = new ArrayList<>();
		languageBaekJoonList.add(Language.create("RUBY", LanguageType.valueOf("BAEKJOON")));
		languageBaekJoonList.add(Language.create("C#", LanguageType.valueOf("BAEKJOON")));
		languageBaekJoonList.add(Language.create("SWIFT", LanguageType.valueOf("BAEKJOON")));
		languageBaekJoonList.add(Language.create("PYTHON", LanguageType.valueOf("BAEKJOON")));
		when(languageQueryRepository.findLanguageByType("BAEKJOON")).thenReturn(languageBaekJoonList);

		//when
		List<LanguageResponse> languageGithubResponses = rankFilterService.getLanguageList("GITHUB");
		List<LanguageResponse> languageBaekjoonResponses = rankFilterService.getLanguageList("BAEKJOON");

		//then
		Assertions.assertNotNull(languageGithubResponses);
		Assertions.assertNotNull(languageBaekjoonResponses);

		Assertions.assertEquals(languageGithubResponses.size(), languageBaekjoonResponses.size());
	}
}