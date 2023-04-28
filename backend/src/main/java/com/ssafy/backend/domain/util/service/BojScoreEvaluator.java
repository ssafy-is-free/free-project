package com.ssafy.backend.domain.util.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ssafy.backend.domain.entity.Baekjoon;

public class BojScoreEvaluator {
	public static int scoreEvaluator(Baekjoon baekjoon) {
		//제출문제 - (틀린문제 / 맞은문제) * 제출 - 시도했지만 맞은 문제 * (티어 / 맞은문제)
		double score = Math.ceil(((double)baekjoon.getFailCount() / baekjoon.getPassCount()) * baekjoon.getSubmitCount()
			- baekjoon.getTryFailCount() * (getTierNumberFromUrl(baekjoon.getTier()) / (double)baekjoon.getPassCount())
			* 0.5);
		return (int)score;
	}

	// 티어에서 숫자만 파싱하는 함수
	private static int getTierNumberFromUrl(String tier) {
		Pattern pattern = Pattern.compile("tier/(\\d+)");
		Matcher matcher = pattern.matcher(tier);

		if (matcher.find()) {
			return Integer.parseInt(matcher.group(1));
		} else {
			return 0;
		}
	}
}
