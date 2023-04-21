package com.ssafy.backend.domain.github.repository.querydsl;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.jpa.impl.JPAQueryFactory;

public class GithubRepositorySupportImpl extends QuerydslRepositorySupport {
	private final JPAQueryFactory queryFactory;

	public GithubRepositorySupportImpl(Class<?> domainClass, JPAQueryFactory queryFactory) {
		super(domainClass);
		this.queryFactory = queryFactory;
	}

}
