package com.ssafy.backend.domain.job.repository;

import static com.ssafy.backend.domain.entity.QGithub.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.entity.JobHistory;
import com.ssafy.backend.domain.entity.JobPosting;
import com.ssafy.backend.domain.entity.QJobHistory;
import com.ssafy.backend.domain.entity.QJobPosting;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class JobHistoryQueryRepository {

	private final JPAQueryFactory queryFactory;

	//해당 취업공고에 지원한 취업이력정보 조회
	public List<JobHistory> findByPostingId(long jobPostingId) {

		QJobHistory jobHistory = QJobHistory.jobHistory;

		return queryFactory
			.selectFrom(jobHistory)
			.where(jobHistory.jobPosting.id.eq(jobPostingId))
			.fetch();

	}

	public List<Long> findByPostingJonGithub(JobPosting jobPosting) {

		QJobHistory jobHistory = QJobHistory.jobHistory;

		return queryFactory
			.select(github.id)
			.from(jobHistory)
			.where(jobHistory.jobPosting.eq(jobPosting))
			.leftJoin(github).on(jobHistory.user.id.eq(github.user.id))
			.fetch();

	}

	//해당 유저가 등록한 취업 이력 조회
	// TODO : orderby조건에 dday와 현재시간과의 차이를 계산에서 정렬하도록 넣어야됨 - querydsl문법을 못찾았음.
	public List<JobHistory> findByUserJoinPosting(long userId, List<Long> statusIdList, String nextDate,
		Long jobHistoryId, Pageable pageable) {

		QJobHistory jobHistory = QJobHistory.jobHistory;
		QJobPosting jobPosting = QJobPosting.jobPosting;

		return queryFactory
			.selectFrom(jobHistory)
			.leftJoin(jobHistory.jobPosting, jobPosting).fetchJoin()
			.where(jobHistory.user.id.eq(userId), jobHistory.isDeleted.eq(false), inStatusId(statusIdList)
				, cursorCondition(nextDate, jobHistoryId))
			.orderBy(jobHistory.dDay.asc(), jobHistory.createdTime.desc())
			.limit(pageable.getPageSize())
			.fetch();
	}

	//취업 이력 상세 조회
	public Optional<JobHistory> findByIdJoinPosting(long userId, long jobHistoryId) {

		QJobHistory jobHistory = QJobHistory.jobHistory;
		QJobPosting jobPosting = QJobPosting.jobPosting;

		return Optional.ofNullable(
			queryFactory
				.selectFrom(jobHistory)
				.leftJoin(jobHistory.jobPosting, jobPosting).fetchJoin()
				.where(jobHistory.isDeleted.eq(false), jobHistory.user.id.eq(userId), jobHistory.id.eq(jobHistoryId))
				.fetchOne()
		);
	}

	//해당 공고 지원자 수.
	public Long countUserTotalJobHistory(long jobPostingId) {
		QJobHistory jobHistory = QJobHistory.jobHistory;

		return queryFactory
			.select(jobHistory.count())
			.from(jobHistory)
			.where(jobHistory.jobPosting.id.eq(jobPostingId),
				jobHistory.isDeleted.eq(false))
			.fetchFirst();
	}

	//업데이트 벌크연산 - 삭제 처리
	public long deleteBulk(long userId, List<Long> jobHistoryIds) {

		QJobHistory jobHistory = QJobHistory.jobHistory;

		return queryFactory
			.update(jobHistory)
			.set(jobHistory.isDeleted, true)
			.where(jobHistory.user.id.eq(userId), jobHistory.id.in(jobHistoryIds))
			.execute();
	}

	private BooleanExpression inStatusId(List<Long> statusIdList) {

		if (statusIdList == null || statusIdList.isEmpty()) {
			return null;
		}

		return QJobHistory.jobHistory.statusId.in(statusIdList);
	}

	private BooleanExpression cursorCondition(String nextDate, Long jobHistoryId) {

		QJobHistory jobHistory = QJobHistory.jobHistory;

		if (nextDate == null || jobHistoryId == null) {
			return null;
		}

		return jobHistory.dDay.gt(LocalDate.parse(nextDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.or(jobHistory.dDay.eq(LocalDate.parse(nextDate)).and(jobHistory.id.gt(jobHistoryId)));
	}

}
