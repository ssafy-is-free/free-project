package com.ssafy.backend.domain.github.repository.querydsl;

import static com.ssafy.backend.domain.entity.QGithub.*;
import static com.ssafy.backend.domain.entity.QRank.*;
import static com.ssafy.backend.domain.entity.QUser.*;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.entity.Github;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GithubRepositoryImpl implements GithubRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<Github> findAll(String lastId, Pageable pageable) {
		return queryFactory.select(github)
			.from(github)
			.innerJoin(github.user, user)
			.fetchJoin()
			.innerJoin(user.rank, rank)
			.fetchJoin()
			.where(lastIdLt(lastId))
			.orderBy(github.score.asc())
			.limit(pageable.getPageSize())
			.fetch();
	}

	private StringExpression getLastId() {
		return StringExpressions.lpad(github.score.stringValue(), 10, '0')
			.concat(StringExpressions.lpad(github.id.stringValue(), 10, '0'));
	}

	private BooleanExpression lastIdLt(String last) {
		return last != null ? getLastId().lt(last) : null;
	}

}
