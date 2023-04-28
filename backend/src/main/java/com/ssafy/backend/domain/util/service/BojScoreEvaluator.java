package com.ssafy.backend.domain.util.service;

import com.ssafy.backend.domain.entity.Baekjoon;

public class BojScoreEvaluator {
	private final int SCALE = 10000;

	public int scoreEvaluator(Baekjoon baekjoon) {
		//제출문제 - (틀린문제 / 맞은문제) * 제출 - 시도했지만 맞은 문제 * (티어 / 맞은문제)

		//스케일링 10000까지만 점수가 포함되게

		return 0;
	}

	// 티어에서 숫자만 파싱하는 함수
	public int parser(String tier) {

		return 0;
	}
}
