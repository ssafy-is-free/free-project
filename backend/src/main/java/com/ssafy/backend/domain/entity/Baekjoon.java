package com.ssafy.backend.domain.entity;

import static javax.persistence.GenerationType.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.ssafy.backend.domain.algorithm.dto.response.CBojInfoResponse;
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
@Table(name = "baekjoon")
public class Baekjoon extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "tier", nullable = false)
	private String tier;

	@Column(name = "pass_count", nullable = false)
	private int passCount;

	@Column(name = "try_fail_count", nullable = false)
	private int tryFailCount;

	@Column(name = "submit_count", nullable = false)
	private int submitCount;

	@Column(name = "fail_count", nullable = false)
	private int failCount;

	@Column(name = "score", nullable = false)
	private int score;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;

	@Column(name = "boj_previous_rank", nullable = false)
	private long previousRank;

	public static Baekjoon createBaekjoon(CBojInfoResponse CBojInfoResponse, User user, int score) {
		return Baekjoon.builder()
			.tier(CBojInfoResponse.getTier())
			.passCount(CBojInfoResponse.getPassCount())
			.tryFailCount(CBojInfoResponse.getTryFailCount())
			.submitCount(CBojInfoResponse.getSubmitCount())
			.failCount(CBojInfoResponse.getFailCount())
			.user(user)
			.score(score)
			.build();
	}

	public void updateBaekjoon(CBojInfoResponse CBojInfoResponse, int score) {
		this.tier = CBojInfoResponse.getTier();
		this.passCount = CBojInfoResponse.getPassCount();
		this.tryFailCount = CBojInfoResponse.getTryFailCount();
		this.submitCount = CBojInfoResponse.getSubmitCount();
		this.failCount = CBojInfoResponse.getFailCount();
		this.score = score;
	}

	public void updatePrevRankBaekjoon(long previousRank) {
		this.previousRank = previousRank;
	}
}
