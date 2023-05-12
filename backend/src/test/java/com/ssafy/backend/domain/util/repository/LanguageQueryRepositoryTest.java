package com.ssafy.backend.domain.util.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.common.LanguageType;

@DataJpaTest
@ActiveProfiles("test")
class LanguageQueryRepositoryTest {

	private LanguageQueryRepository languageQueryRepository;

	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private TestEntityManager testEntityManager;
	private EntityManager entityManager;
	private JPAQueryFactory queryFactory;

	@BeforeEach
	void setup() {

		entityManager = testEntityManager.getEntityManager();
		queryFactory = new JPAQueryFactory(entityManager);
		languageQueryRepository = new LanguageQueryRepository(queryFactory);

		List<Language> languageList = new ArrayList<>();
		languageList.add(createLanguage("C++17", LanguageType.valueOf("BAEKJOON")));
		languageList.add(createLanguage("JAVA17", LanguageType.valueOf("BAEKJOON")));
		languageList.add(createLanguage("PYPY", LanguageType.valueOf("BAEKJOON")));
		languageList.add(createLanguage("PYTHON3", LanguageType.valueOf("BAEKJOON")));

		languageList.add(createLanguage("C++", LanguageType.valueOf("GITHUB")));
		languageList.add(createLanguage("JAVA", LanguageType.valueOf("GITHUB")));
		languageList.add(createLanguage("PYTHON", LanguageType.valueOf("GITHUB")));

		languageRepository.saveAll(languageList);
	}

	@AfterEach
	void tearDown() {
		languageRepository.deleteAllInBatch();
		this.entityManager
			.createNativeQuery(
				"ALTER TABLE language ALTER COLUMN `id` RESTART                                                                     WITH 1")
			.executeUpdate();
	}

	@Test
	@DisplayName("타입별 언어 조회 - 백준")
	void findLanguageByTypeBoj() {
		//given
		LanguageType languageType = LanguageType.valueOf("BAEKJOON");

		//when
		List<Language> languageList = languageQueryRepository.findLanguageByType(languageType);

		//then
		Assertions.assertThat(languageList)
			.isNotNull()
			.hasSize(4)
			.extracting("name")
			.contains("C++17", "JAVA17", "PYPY", "PYTHON3");
	}

	@Test
	@DisplayName("타입별 언어 조회 - 깃허브")
	void findLanguageByTypeGithub() {
		//given
		LanguageType languageType = LanguageType.valueOf("GITHUB");

		//when
		List<Language> languageList = languageQueryRepository.findLanguageByType(languageType);

		//then
		Assertions.assertThat(languageList)
			.isNotNull()
			.hasSize(3)
			.extracting("name")
			.contains("C++", "JAVA", "PYTHON");

	}

	public Language createLanguage(String name, LanguageType type) {

		return Language.builder()
			.name(name)
			.type(type)
			.build();
	}

}