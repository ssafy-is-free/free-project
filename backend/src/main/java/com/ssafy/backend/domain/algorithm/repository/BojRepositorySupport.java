package com.ssafy.backend.domain.algorithm.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.entity.Baekjoon;

@Repository
public class BojRepositorySupport extends QuerydslRepositorySupport {
	private final JPAQueryFactory queryFactory;

	public BojRepositorySupport(JPAQueryFactory queryFactory) {
		super(Baekjoon.class);
		this.queryFactory = queryFactory;
	}

}
