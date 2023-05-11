package com.ssafy.backend.domain.util.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TierValueFormatterTest {
	@Test
	@DisplayName("숫자 -> 티어 변환 테스트")
	public void formatTest() {
		//given
		int tier = 15;
		//when
		String formatTier = TierValueFormatter.format(tier);

		//then
		assertThat(formatTier).isEqualTo("https://d2gd6pc034wcta.cloudfront.net/tier/15.svg");
	}

	@Test
	@DisplayName("티어 -> 숫자 변환 테스트")
	public void reFormatTest() {
		//given
		String tier = "https://d2gd6pc034wcta.cloudfront.net/tier/15.svg";
		//when
		int reFormatTier = TierValueFormatter.reFormat(tier);

		//then
		assertThat(reFormatTier).isEqualTo(15);
	}

	@Test
	@DisplayName("티어 변환 인스턴스 테스트")
	public void tierValueFormatterInstanceTest() {
		//given
		TierValueFormatter tierValueFormatter = new TierValueFormatter();
		int tier = 15;
		//when
		String formatTier = tierValueFormatter.format(tier);

		//then
		assertThat(formatTier).isEqualTo("https://d2gd6pc034wcta.cloudfront.net/tier/15.svg");
	}
}
