package com.ssafy.backend.domain.github.repository.querydsl;

import static com.ssafy.backend.domain.entity.QGithub.*;
import static com.ssafy.backend.domain.entity.QUser.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.github.dto.FilteredGithubIdSet;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GithubQueryRepository {
	private final JPAQueryFactory queryFactory;

	public List<Github> findAll(Long userId, Integer score, FilteredGithubIdSet githubIdSet, Pageable pageable) {

		return queryFactory.select(github)
			.from(github)
			.innerJoin(github.user, user)
			.fetchJoin()
			.where(githubIdIn(githubIdSet), checkCursor(score, userId))
			.orderBy(github.score.desc())
			.limit(pageable.getPageSize())
			.fetch();
	}

	private BooleanExpression githubIdIn(FilteredGithubIdSet githubIdSet) {
		return githubIdSet != null ? github.id.in(githubIdSet.getGithubIds()) : null;
	}

	private BooleanExpression checkCursor(Integer score, Long githubId) {
		if (score == null || githubId == null) {
			return null;

		}
		return scoreLt(score).or(scoreEqAndGithubIdGt(score, githubId));
	}

	private BooleanExpression scoreLt(Integer score) {
		return score != null ? github.score.lt(score) : null;
	}

	private BooleanExpression scoreEq(Integer score) {
		return score != null ? github.score.eq(score) : null;
	}

	private BooleanExpression userIdGt(Long userId) {
		return userId != null ? github.user.id.gt(userId) : null;
	}

	private BooleanExpression scoreEqAndGithubIdGt(Integer score, Long githubId) {
		return scoreEq(score).and(userIdGt(githubId));
	}
}
