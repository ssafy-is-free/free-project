package com.ssafy.backend.domain.entity;

import static javax.persistence.GenerationType.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.ssafy.backend.domain.algorithm.dto.response.CBojLanguageResultResponse;
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
@Table(name = "baekjoon_languages")
public class BaekjoonLanguage extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "language_id", nullable = false)
	private long languageId;

	@Column(name = "pass_percentage", nullable = false)
	private String passPercentage;

	@Column(name = "pass_count", nullable = false)
	private int passCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "baekjoon_id", nullable = false)
	private Baekjoon baekjoon;

	public static BaekjoonLanguage createBaekjoonLanguage(Long languageId,
		CBojLanguageResultResponse CBojLanguageResultResponse, Baekjoon baekjoon) {
		return BaekjoonLanguage.builder()
			.languageId(languageId)
			.passPercentage(CBojLanguageResultResponse.getPassPercentage())
			.passCount(CBojLanguageResultResponse.getPassCount())
			.baekjoon(baekjoon)
			.build();

	}

	public void updateBaekjoonLanguage(CBojLanguageResultResponse CBojLanguageResultResponse) {
		this.passPercentage = CBojLanguageResultResponse.getPassPercentage();
		this.passCount = CBojLanguageResultResponse.getPassCount();
	}
}
