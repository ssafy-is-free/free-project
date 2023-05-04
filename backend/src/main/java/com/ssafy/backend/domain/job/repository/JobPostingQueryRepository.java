package com.ssafy.backend.domain.job.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.entity.JobPosting;
import com.ssafy.backend.domain.entity.QJobPosting;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class JobPostingQueryRepository {

	private final JPAQueryFactory queryFactory;

	//키워드로 지원 상태 조회.
	public List<JobPosting> findByNameLikeAndIsCloseFalse(String keyword) {

		QJobPosting jobPosting = QJobPosting.jobPosting;

		return queryFactory
			.selectFrom(jobPosting)
			.where(likeKeyword(keyword))
			.fetch();
	}

	private BooleanExpression likeKeyword(String keyword) {

		if (!StringUtils.hasText(keyword))
			return null;
		
		return QJobPosting.jobPosting.name.contains(keyword);
	}
}
