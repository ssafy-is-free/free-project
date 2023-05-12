package com.ssafy.backend.domain.util.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ssafy.backend.domain.algorithm.dto.response.CBojInfoResponse;

public class BojScoreEvaluatorTest {

	@Test
	@DisplayName("스코어 변환 테스트")
	public void scoreEvaluatorTest() {
		//given
		CBojInfoResponse cBojInfoResponse = createCBojInfoResponse(100, 150);
		//when
		int score = BojScoreEvaluator.scoreEvaluator(cBojInfoResponse);
		//then
		assertThat(score).isEqualTo(1508);
	}

	@Test
	@DisplayName("스코어 변환 pass, fail 동일 테스트")
	public void scoreEvaluatorPassFailTest() {
		//given
		CBojInfoResponse cBojInfoResponse = createCBojInfoResponse(100, 100);
		//when
		int score = BojScoreEvaluator.scoreEvaluator(cBojInfoResponse);
		//then
		assertThat(score).isEqualTo(1500);
	}

	@Test
	@DisplayName("스코어 변환 인스턴스 테스트")
	public void scoreEvaluatorInstanceTest() {
		//given
		BojScoreEvaluator bojScoreEvaluator = new BojScoreEvaluator();
		CBojInfoResponse cBojInfoResponse = createCBojInfoResponse(100, 150);
		//when
		int score = bojScoreEvaluator.scoreEvaluator(cBojInfoResponse);
		//then
		assertThat(score).isEqualTo(1508);
	}

	private CBojInfoResponse createCBojInfoResponse(int failCount, int passCount) {
		CBojInfoResponse cBojInfoResponse = new CBojInfoResponse();
		cBojInfoResponse.setTier("https://d2gd6pc034wcta.cloudfront.net/tier/15.svg");
		cBojInfoResponse.setFailCount(failCount);
		cBojInfoResponse.setPassCount(passCount);
		cBojInfoResponse.setTryFailCount(100);
		cBojInfoResponse.setSubmitCount(250);

		return cBojInfoResponse;
	}
}
