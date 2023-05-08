package com.ssafy.backend.domain.github.repository.querydsl;

import static com.ssafy.backend.domain.entity.QGithubLanguage.*;
import static com.ssafy.backend.domain.entity.QLanguage.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.github.dto.FilteredGithubIdSet;
import com.ssafy.backend.domain.github.dto.GithubDetailLanguage;
import com.ssafy.backend.domain.github.dto.QGithubDetailLanguage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class GithubLanguageQueryRepository {
	private final JPAQueryFactory queryFactory;

	public List<GithubDetailLanguage> findByGithub(Github github) {
		return queryFactory.select(new QGithubDetailLanguage(language.name, githubLanguage.percentage))
			.from(githubLanguage)
			.leftJoin(language)
			.on(githubLanguage.languageId.eq(language.id))
			.where(githubEq(github))
			.fetch();
	}

	public List<GithubDetailLanguage> findAvgGroupByLanguage(FilteredGithubIdSet filteredGithubIdSet) {
		return queryFactory.select(new QGithubDetailLanguage(language.name, githubLanguage.percentage.sum()))
			.from(githubLanguage)
			.leftJoin(language)
			.on(githubLanguage.languageId.eq(language.id))
			.where(githubIdIn(filteredGithubIdSet))
			.groupBy(githubLanguage.languageId)
			.orderBy(githubLanguage.percentage.sum().desc())
			.limit(5)
			.fetch();

		// 얻은 아이디를 where 조건에 넣고 깃허브언어에서 언어 아이디로 Group By 후 percent 평균
	}

	private BooleanExpression githubEq(Github github) {
		return github != null ? githubLanguage.github.eq(github) : null;
	}

	private BooleanExpression githubIdIn(FilteredGithubIdSet githubIdSet) {
		return githubIdSet != null ? githubLanguage.github.id.in(githubIdSet.getGithubIds()) : null;
	}
}
