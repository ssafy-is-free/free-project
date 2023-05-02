package com.ssafy.backend.domain.job.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.entity.JobHistory;
import com.ssafy.backend.domain.entity.QJobHistory;
import com.ssafy.backend.domain.entity.QUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class JobHistoryQueryRepository {

	private final JPAQueryFactory queryFactory;

	//해당 취업공고에 지원한 취업이력정보 조회
	// TODO: 2023-05-02 하나의 트랜잭션안에서 조회하기 때문에 jobposting 테이블을 조인할 필요는 없음
	public List<JobHistory> findByPostingIdJoinUser(long jobPostingId) {

		QJobHistory jobHistory = QJobHistory.jobHistory;
		QUser user = QUser.user;

		return queryFactory
			.selectFrom(jobHistory)
			.leftJoin(jobHistory.user, user).fetchJoin()
			.where(jobHistory.jobPosting.id.eq(jobPostingId))
			.fetch();

	}
}
