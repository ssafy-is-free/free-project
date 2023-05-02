package com.ssafy.backend.domain.algorithm.repository;

import static com.ssafy.backend.domain.entity.QBaekjoon.*;
import static com.ssafy.backend.domain.entity.QUser.*;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.algorithm.dto.FilteredBojIdSet;
import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.QBaekjoon;
import com.ssafy.backend.domain.entity.QUser;
import com.ssafy.backend.domain.github.dto.FilteredUserIdSet;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BojQueryRepository {
	private final JPAQueryFactory queryFactory;

	public Long findRankByScore(long userId, FilteredUserIdSet userIdSet, FilteredBojIdSet bojIdSet, int score) {
		return queryFactory.select(baekjoon.count())
			.from(baekjoon)
			.innerJoin(baekjoon.user, user)
			.where(bojIdIn(bojIdSet), userIdIn(userIdSet), user.id.ne(userId), baekjoon.score.lt(score))
			.fetchOne();

	}

	// TODO: 2023-04-28 언어별, 그룹별 추가 필요
	public List<Baekjoon> findAllByScore(Set<Long> baekjoonIdSet, String group, Integer score, Long languageId,
		Long userId, Pageable pageable) {

		QBaekjoon baekjoon = QBaekjoon.baekjoon;
		QUser user = QUser.user;

		return queryFactory
			.selectFrom(baekjoon)
			.leftJoin(baekjoon.user, user).fetchJoin()
			.where(cursorCondition(score, userId),
				inBaekjoonId(baekjoonIdSet, languageId))
			.orderBy(baekjoon.score.desc(),
				baekjoon.user.id.asc())
			.limit(pageable.getPageSize())
			.fetch();
	}

	//마지막 점수보다 작거나 같은것
	private BooleanExpression cursorCondition(Integer score, Long userId) {

		QBaekjoon baekjoon = QBaekjoon.baekjoon;

		if (score == null || userId == null)
			return null;

		//마지막 스코어 > 테이블 스코어 or (마지막 스코어 = 테이블 스코어 and 마지막 유저 id > 테이블 유저)
		return baekjoon.score.lt(score).or(baekjoon.score.eq(score).and(baekjoon.user.id.gt(userId)));
	}

	private BooleanExpression inBaekjoonId(Set<Long> baekjoonIdSet, Long languageId) {

		if (baekjoonIdSet == null || languageId == null)
			return null;

		return baekjoon.id.in(baekjoonIdSet);

	}

	private BooleanExpression bojIdIn(FilteredBojIdSet baekjoonIdSet) {
		return baekjoonIdSet != null ? baekjoon.id.in(baekjoonIdSet.getBojIds()) : null;
	}

	private BooleanExpression userIdIn(FilteredUserIdSet userIdSet) {
		return userIdSet != null ? baekjoon.user.id.in(userIdSet.getUserIds()) : null;
	}

}
