package com.ssafy.backend.domain.util.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TierValueFormatter {
	public static String format(double value) {
		int intValue = (int)value;
		return "https://d2gd6pc034wcta.cloudfront.net/tier/" + intValue + ".svg";
	}

	// 티어에서 숫자만 파싱하는 함수
	public static int reFormat(String tier) {
		Pattern pattern = Pattern.compile("tier/(\\d+)");
		Matcher matcher = pattern.matcher(tier);

		return matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
	}

	public static int reFormat(int tier) {
		return (tier == 0) ? 1 : tier;
	}
}
