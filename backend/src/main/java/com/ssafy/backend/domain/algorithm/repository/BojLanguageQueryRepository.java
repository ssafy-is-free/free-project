package com.ssafy.backend.domain.algorithm.repository;

import static com.ssafy.backend.domain.entity.QBaekjoonLanguage.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.analysis.dto.BojLanguagePassCount;
import com.ssafy.backend.domain.analysis.dto.QBojLanguagePassCount;
import com.ssafy.backend.domain.entity.BaekjoonLanguage;
import com.ssafy.backend.domain.entity.QBaekjoon;
import com.ssafy.backend.domain.entity.QBaekjoonLanguage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BojLanguageQueryRepository {

	private final JPAQueryFactory queryFactory;

	/*language로 백준식별자 조회*/

	public List<BaekjoonLanguage> findBojLanguageByLanguage(Long language) {

		QBaekjoonLanguage baekjoonLanguage = QBaekjoonLanguage.baekjoonLanguage;
		QBaekjoon baekjoon = QBaekjoon.baekjoon;

		return queryFactory
			.selectFrom(baekjoonLanguage)
			.leftJoin(baekjoonLanguage.baekjoon, baekjoon).fetchJoin()
			.where(baekjoonLanguage.languageId.eq(language))
			.fetch();
	}

	public List<BojLanguagePassCount> findBojLanguagePassCount() {
		return queryFactory
			.select(new QBojLanguagePassCount(baekjoonLanguage.languageId,
				baekjoonLanguage.passCount.avg()))
			.from(baekjoonLanguage)
			.groupBy(baekjoonLanguage.languageId)
			.orderBy(baekjoonLanguage.passCount.avg().desc())
			.limit(5)
			.fetch();
	}
}
