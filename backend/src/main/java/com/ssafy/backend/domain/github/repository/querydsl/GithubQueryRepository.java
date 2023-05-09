package com.ssafy.backend.domain.github.repository.querydsl;

import static com.ssafy.backend.domain.entity.QGithub.*;
import static com.ssafy.backend.domain.entity.QUser.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.analysis.dto.response.GithubVsInfo;
import com.ssafy.backend.domain.analysis.dto.response.QGithubVsInfo;
import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.github.dto.FilteredGithubIdSet;
import com.ssafy.backend.domain.github.dto.FilteredUserIdSet;

import io.lettuce.core.dynamic.annotation.Param;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GithubQueryRepository {
	private final JPAQueryFactory queryFactory;

	//삭제되지 않은 유저의 깃허브 조회
	public Optional<Github> findOneByIdNotDeleted(long githubId) {

		return Optional.ofNullable(queryFactory
			.selectFrom(github)
			.where(github.id.eq(githubId), github.user.isDeleted.eq(false))
			.fetchOne());
	}

	public List<Github> findAll(Long userId, Integer score, FilteredGithubIdSet githubIdSet,
		FilteredUserIdSet userIdSet, Pageable pageable) {
		return queryFactory.select(github)
			.from(github)
			.innerJoin(github.user, user)
			.fetchJoin()
			.where(githubIdIn(githubIdSet), userIdIn(userIdSet), checkCursor(score, userId))
			.orderBy(github.score.desc(), github.user.id.asc())
			.limit(pageable.getPageSize())
			.fetch();
	}

	public GithubVsInfo findAvgByApplicant(FilteredUserIdSet filteredUserIdSet) {
		return queryFactory.select(new QGithubVsInfo(github.commitTotalCount.avg(), github.starTotalCount.avg()))
			.from(github)
			.where(userIdIn(filteredUserIdSet))
			.fetchOne();

	}

	public Long getRankWithFilter(FilteredGithubIdSet githubIdSet, FilteredUserIdSet userIdSet,
		@Param("score") int score,
		@Param("userId") long userId) {
		return queryFactory.select(github.count())
			.from(github)
			.where(githubIdIn(githubIdSet), userIdIn(userIdSet), higherRanked(score, userId))
			.fetchOne();

	}

	private BooleanExpression githubIdIn(FilteredGithubIdSet githubIdSet) {
		return githubIdSet != null ? github.id.in(githubIdSet.getGithubIds()) : null;
	}

	private BooleanExpression userIdIn(FilteredUserIdSet userIdSet) {
		return userIdSet != null ? github.user.id.in(userIdSet.getUserIds()) : null;
	}

	private BooleanExpression checkCursor(Integer score, Long userId) {
		if (score == null || userId == null) {
			return null;

		}
		return scoreLt(score).or(scoreEqAndUserIdGt(score, userId));
	}

	private BooleanExpression higherRanked(Integer score, Long userId) {
		if (score == null || userId == null) {
			return null;

		}
		return scoreGt(score).or(scoreEqAndUserIdLt(score, userId));
	}

	private BooleanExpression scoreLt(Integer score) {
		return score != null ? github.score.lt(score) : null;
	}

	private BooleanExpression scoreGt(Integer score) {
		return score != null ? github.score.gt(score) : null;
	}

	private BooleanExpression scoreEq(Integer score) {
		return score != null ? github.score.eq(score) : null;
	}

	private BooleanExpression userIdGt(Long userId) {
		return userId != null ? github.user.id.gt(userId) : null;
	}

	private BooleanExpression userIdLt(Long userId) {
		return userId != null ? github.user.id.lt(userId) : null;
	}

	private BooleanExpression scoreEqAndUserIdGt(Integer score, Long userId) {
		if (score == null || userId == null) {
			return null;
		}
		return scoreEq(score).and(userIdGt(userId));
	}

	private BooleanExpression scoreEqAndUserIdLt(Integer score, Long userId) {
		if (score == null || userId == null) {
			return null;
		}
		return scoreEq(score).and(userIdLt(userId));
	}
}
