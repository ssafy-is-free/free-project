package com.ssafy.backend.domain.job.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.entity.JobHistory;
import com.ssafy.backend.domain.entity.QJobHistory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class JobHistoryQueryRepository {

	private final JPAQueryFactory queryFactory;

	//해당 취업공고에 지원한 취업이력정보 조회
	public List<JobHistory> findByPostingIdJoinUser(long jobPostingId) {

		QJobHistory jobHistory = QJobHistory.jobHistory;

		return queryFactory
			.selectFrom(jobHistory)
			.where(jobHistory.jobPosting.id.eq(jobPostingId))
			.fetch();

	}
}
