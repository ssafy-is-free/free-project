package com.ssafy.backend.domain.util.service;

public class TierValueFormatter {
	public static String format(double value) {
		int intValue = (int)value;
		return "https://d2gd6pc034wcta.cloudfront.net/tier/" + intValue + ".svg";
	}
}
