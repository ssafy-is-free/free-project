package com.ssafy.backend.domain.algorithm.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.algorithm.dto.response.BojMyRankResponseDTO;
import com.ssafy.backend.domain.algorithm.dto.response.QBojMyRankResponseDTO;
import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.QBaekjoon;
import com.ssafy.backend.domain.entity.QUser;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.backend.domain.entity.QBaekjoon.baekjoon;
import static com.ssafy.backend.domain.entity.QUser.user;


@Repository
public class BojRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;
    public BojRepositorySupport(JPAQueryFactory queryFactory) {
        super(Baekjoon.class);
        this.queryFactory = queryFactory;
    }

}
