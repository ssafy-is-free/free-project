package com.ssafy.backend.domain.entity;

import static javax.persistence.GenerationType.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.ssafy.backend.domain.entity.common.BaseTimeEntity;
import com.ssafy.backend.domain.job.dto.JobApplyRegistrationRequest;
import com.ssafy.backend.domain.job.dto.JobApplyUpdateRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@DynamicUpdate
@NoArgsConstructor
@Table(name = "job_history")
public class JobHistory extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "d_day", nullable = false)
	private LocalDate dDay;

	@Column(name = "d_day_name", nullable = false)
	private String dDayName;

	@Column(name = "memo")
	private String memo;

	@Column(name = "status_id", nullable = false)
	private long statusId;

	@Column(name = "job_objective", nullable = false)
	private String jobObjective;

	@Column(name = "is_deleted", nullable = false)
	private boolean isDeleted;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_posting_id", nullable = false)
	private JobPosting jobPosting;

	public static JobHistory create(JobApplyRegistrationRequest jobApplyRegistrationRequest, User user,
		JobPosting jobPosting) {

		String createDDayName = jobApplyRegistrationRequest.getDDayName() == null ?
			"접수마감일" :
			jobApplyRegistrationRequest.getDDayName();

		//값이 없으면 공고 마감일, 있으면 사용자가 등록한 값.
		LocalDate createDDay = jobApplyRegistrationRequest.getDDay() == null ?
			jobPosting.getEndTime().toLocalDate() :
			LocalDate.parse(jobApplyRegistrationRequest.getDDay(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		return JobHistory.builder()
			.dDay(createDDay)
			.dDayName(createDDayName)
			.memo(jobApplyRegistrationRequest.getMemo())
			.statusId(jobApplyRegistrationRequest.getStatusId())
			.jobObjective(jobApplyRegistrationRequest.getObjective())
			.isDeleted(false)
			.user(user)
			.jobPosting(jobPosting)
			.build();
	}

	//TODO 좀 더 깔끔하게 짤 방법 필요.
	public void update(JobApplyUpdateRequest jobApplyUpdateRequest) {

		if (jobApplyUpdateRequest.getStatusId() != null) {
			this.statusId = jobApplyUpdateRequest.getStatusId();
		}
		if (jobApplyUpdateRequest.getMemo() != null) {
			this.memo = jobApplyUpdateRequest.getMemo();
		}
		if (jobApplyUpdateRequest.getDDayName() != null) {
			this.dDayName = jobApplyUpdateRequest.getDDayName();
		}
		if (jobApplyUpdateRequest.getNextDate() != null) {
			this.dDay = LocalDate.parse(jobApplyUpdateRequest.getNextDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		if (jobApplyUpdateRequest.getObjective() != null) {
			this.jobObjective = jobApplyUpdateRequest.getObjective();
		}
	}

}
