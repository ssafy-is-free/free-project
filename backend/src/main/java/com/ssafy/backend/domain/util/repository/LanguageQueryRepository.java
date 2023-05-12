package com.ssafy.backend.domain.util.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.QLanguage;
import com.ssafy.backend.domain.entity.common.LanguageType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class LanguageQueryRepository {

	private final JPAQueryFactory queryFactory;

	public List<Language> findLanguageByType(LanguageType type) {

		QLanguage language = QLanguage.language;

		return queryFactory
			.selectFrom(language)
			.where(typeEq(type))
			.fetch();
	}

	//랭킹 필터
	private BooleanExpression typeEq(LanguageType type) {

		if (type == null) {
			return null;
		}

		return QLanguage.language.type.eq(type);
	}
}
