package com.ssafy.backend.domain.algorithm.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.QBaekjoon;
import com.ssafy.backend.domain.entity.QUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BojQueryRepository {
	private final JPAQueryFactory queryFactory;

	// TODO: 2023-04-28 언어별, 그룹별 추가 필요
	public List<Baekjoon> findAllByScore(Set<Long> baekjoonIdSet, Set<Long> jobUserId, String group, Integer score,
		Long userId, Pageable pageable) {

		QBaekjoon baekjoon = QBaekjoon.baekjoon;
		QUser user = QUser.user;

		return queryFactory
			.selectFrom(baekjoon)
			.leftJoin(baekjoon.user, user).fetchJoin()
			.where(cursorCondition(score, userId),
				inLanguageBaekjoonId(baekjoonIdSet),
				inJobUserId(jobUserId))
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

	private BooleanExpression inLanguageBaekjoonId(Set<Long> baekjoonIdSet) {

		if (baekjoonIdSet == null || baekjoonIdSet.isEmpty())
			return null;

		return QBaekjoon.baekjoon.id.in(baekjoonIdSet);

	}

	private BooleanExpression inJobUserId(Set<Long> jobUserId) {

		if (jobUserId == null || jobUserId.isEmpty()) {
			return null;
		}

		return QBaekjoon.baekjoon.user.id.in(jobUserId);
	}

}
