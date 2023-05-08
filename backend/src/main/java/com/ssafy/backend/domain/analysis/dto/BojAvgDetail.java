package com.ssafy.backend.domain.analysis.dto;

import java.text.DecimalFormat;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class BojAvgDetail {
	private Double avgFailCount;
	private Double avgPassCount;
	private Double avgTryFailCount;
	private Double avgSubmitCount;
	private Double avgTier;

	@QueryProjection
	public BojAvgDetail(Double avgFailCount, Double avgPassCount, Double avgTryFailCount, Double avgSubmitCount,
		Double avgTier) {
		this.avgFailCount = avgFailCount;
		this.avgPassCount = avgPassCount;
		this.avgTryFailCount = avgTryFailCount;
		this.avgSubmitCount = avgSubmitCount;
		this.avgTier = avgTier;
	}

	public void roundToDecimalPlace() {
		DecimalFormat df = new DecimalFormat("#.#");
		this.avgFailCount = Double.parseDouble(df.format(avgFailCount));
		this.avgPassCount = Double.parseDouble(df.format(avgPassCount));
		this.avgTryFailCount = Double.parseDouble(df.format(avgTryFailCount));
		this.avgSubmitCount = Double.parseDouble(df.format(avgSubmitCount));
		this.avgTier = (double)Math.round(this.avgTier);
	}
}
