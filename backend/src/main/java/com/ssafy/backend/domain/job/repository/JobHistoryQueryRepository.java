package com.ssafy.backend.domain.job.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.entity.JobHistory;
import com.ssafy.backend.domain.entity.QJobHistory;
import com.ssafy.backend.domain.entity.QJobPosting;

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

	//해당 유저가 등록한 취업 공고 조회
	// TODO : orderby조건에 dday와 현재시간과의 차이를 계산에서 정렬하도록 넣어야됨 - querydsl문법을 못찾았음.
	public List<JobHistory> findByUserJoinPosting(long userId, List<Long> statusIdList) {

		QJobHistory jobHistory = QJobHistory.jobHistory;
		QJobPosting jobPosting = QJobPosting.jobPosting;

		return queryFactory
			.selectFrom(jobHistory)
			.leftJoin(jobHistory.jobPosting, jobPosting).fetchJoin()
			.where(jobHistory.user.id.eq(userId), jobHistory.isDeleted.eq(false), inStatusId(statusIdList))
			.fetch();

	}

	private BooleanExpression inStatusId(List<Long> statusIdList) {

		if (statusIdList == null || statusIdList.isEmpty()) {
			return null;
		}

		return QJobHistory.jobHistory.statusId.in(statusIdList);
	}

}
