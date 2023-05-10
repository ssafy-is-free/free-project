package com.ssafy.backend.domain.github.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GithubRankingCoverTest {

	@DisplayName("현재 랭킹을 기준으로 이전 랭킹과의 등락폭을 볼 수 있다.")
	@Test
	void getRankUpDown() {
		//given
		GithubRankingCover githubRankingCover = GithubRankingCover.builder().prevRank(10).build();

		//when
		long rankUpDown = githubRankingCover.getRankUpDown(11);

		//then
		Assertions.assertThat(rankUpDown).isEqualTo(-1L);
	}

	@DisplayName("이전 랭킹이 0이라면 신규유저임으로 등락폭을 제공하지 않는다.")
	@Test
	void getRankUpDownPrevZero() {
		//given
		GithubRankingCover githubRankingCover = GithubRankingCover.builder().prevRank(0).build();

		//when
		long rankUpDown = githubRankingCover.getRankUpDown(10);

		//then
		Assertions.assertThat(rankUpDown).isEqualTo(0L);
	}
}