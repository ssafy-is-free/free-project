package com.ssafy.backend.domain.entity;

import static javax.persistence.GenerationType.*;

import java.time.LocalDate;

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

}
