package com.ssafy.backend.domain.github.repository.querydsl;

import static com.ssafy.backend.domain.entity.QGithubLanguage.*;
import static com.ssafy.backend.domain.entity.QLanguage.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.github.dto.GithubDetailLanguage;
import com.ssafy.backend.domain.github.dto.QGithubDetailLanguage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class GithubLanguageQueryRepository {
	private final JPAQueryFactory queryFactory;

	public List<GithubDetailLanguage> findByGithub(Github github) {
		return queryFactory
			.select(new QGithubDetailLanguage(language.name, githubLanguage.percentage))
			.from(githubLanguage)
			.leftJoin(language)
			.on(githubLanguage.languageId.eq(language.id))
			.where(GithubEq(github))
			.fetch();
	}

	private BooleanExpression GithubEq(Github github) {
		return github != null ? githubLanguage.github.eq(github) : null;
	}
}
