package com.ssafy.backend.domain.util.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ssafy.backend.domain.algorithm.dto.response.CBojInfoResponse;

public class BojScoreEvaluator {
	public static int scoreEvaluator(CBojInfoResponse cBojInfoResponse) {
		//티어 * 100 + (제출문제 - (틀린문제 / 맞은문제) * 제출 - 시도했지만 틀린 문제 * (티어 / 맞은문제)) / 10
		int numTier = getTierNumberFromUrl(cBojInfoResponse.getTier());

		int score = (int)Math.ceil(
			numTier * 100 + ((double)cBojInfoResponse.getSubmitCount()
				- ((double)cBojInfoResponse.getFailCount() / cBojInfoResponse.getPassCount())
				* cBojInfoResponse.getSubmitCount()
				- cBojInfoResponse.getTryFailCount() * (getTierNumberFormat(numTier)
				/ (double)cBojInfoResponse.getPassCount())
				* 0.5) / 10);

		return Math.max(score, 0);
	}

	// 티어에서 숫자만 파싱하는 함수
	private static int getTierNumberFromUrl(String tier) {
		Pattern pattern = Pattern.compile("tier/(\\d+)");
		Matcher matcher = pattern.matcher(tier);

		return matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
	}

	private static int getTierNumberFormat(int tier) {
		return (tier == 0) ? 1 : tier;
	}
}
