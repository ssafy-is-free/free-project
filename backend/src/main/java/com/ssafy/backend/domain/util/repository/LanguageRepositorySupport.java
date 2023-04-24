package com.ssafy.backend.domain.util.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.QLanguage;
import com.ssafy.backend.domain.entity.common.LanguageType;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LanguageRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public LanguageRepositorySupport(JPAQueryFactory queryFactory) {
        super(Language.class);
        this.queryFactory = queryFactory;
    }

    public List<Language> findLanguageByType(String type){

        QLanguage language = QLanguage.language;

        return queryFactory
                .selectFrom(language)
                .where(typeEq(type))
                .fetch();
    }

    //랭킹 필터
    private BooleanExpression typeEq(String type){

        if(type == null) return null;

        return QLanguage.language.type.eq(LanguageType.valueOf(type.toUpperCase()));
    }
}
