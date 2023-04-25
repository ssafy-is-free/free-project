package com.ssafy.backend.domain.github.repository.querydsl;

import static com.ssafy.backend.domain.entity.QGithub.*;
import static com.ssafy.backend.domain.entity.QUser.*;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.entity.Github;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GithubRepositoryImpl implements GithubRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<Github> findAll(Long githubId, Integer score, Pageable pageable) {

		return queryFactory.select(github)
			.from(github)
			.innerJoin(github.user, user)
			.fetchJoin()
			.where(checkCursor(score, githubId))
			.orderBy(github.score.desc())
			.limit(pageable.getPageSize())
			.fetch();
	}

	public BooleanExpression checkCursor(Integer score, Long githubId) {
		if (score == null || githubId == null)
			return null;
		return scoreLt(score).or(scoreEqAndGithubIdGt(score, githubId));
	}

	private BooleanExpression scoreLt(Integer score) {
		return score != null ? github.score.lt(score) : null;
	}

	private BooleanExpression scoreEq(Integer score) {
		return score != null ? github.score.eq(score) : null;
	}

	private BooleanExpression githubIdGt(Long githubId) {
		return githubId != null ? github.id.gt(githubId) : null;
	}

	private BooleanExpression scoreEqAndGithubIdGt(Integer score, Long githubId) {
		return scoreEq(score).and(githubIdGt(githubId));
	}

}
