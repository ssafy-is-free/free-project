package com.ssafy.backend.domain.util.service;

import com.ssafy.backend.domain.algorithm.dto.response.CBojInfoResponse;

public class BojScoreEvaluator {
	public static int scoreEvaluator(CBojInfoResponse cBojInfoResponse) {
		//티어 * 100 + (제출문제 - (틀린문제 / 맞은문제) * 제출 - 시도했지만 틀린 문제 * (티어 / 맞은문제)) / 10
		int numTier = TierValueFormatter.reFormat(cBojInfoResponse.getTier());

		int score = (int)Math.ceil(
			numTier * 100 + ((double)cBojInfoResponse.getSubmitCount()
				- ((double)cBojInfoResponse.getFailCount() / cBojInfoResponse.getPassCount())
				* cBojInfoResponse.getSubmitCount()
				- cBojInfoResponse.getTryFailCount() * (TierValueFormatter.reFormat(numTier)
				/ (double)cBojInfoResponse.getPassCount())
				* 0.5) / 10);

		return Math.max(score, 0);
	}

}
