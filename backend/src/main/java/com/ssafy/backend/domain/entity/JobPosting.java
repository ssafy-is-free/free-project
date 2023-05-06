package com.ssafy.backend.domain.entity;

import static javax.persistence.GenerationType.*;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.ssafy.backend.domain.entity.common.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "job_posting")
public class JobPosting extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "company_name", nullable = false)
	private String companyName;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "start_time", nullable = false)
	private LocalDateTime startTime;

	@Column(name = "end_time", nullable = false)
	private LocalDateTime endTime;

	@Column(name = "is_close", nullable = false)
	private boolean isClose;
}
