package com.ssafy.backend.domain.algorithm.dto.response;

import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.Rank;
import com.ssafy.backend.domain.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BojMyRankResponseDTO {
	private Long userId;
	private String nickname;
	private Integer rank;
	private Integer score;
	private String avatarUrl;
	private Long rankUpDown;
	private String tierUrl;

	public static BojMyRankResponseDTO createBojMyRankResponseDTO(Baekjoon baekjoon, User user, Rank rank, int count) {
		return BojMyRankResponseDTO.builder()
			.userId(user.getId())
			.nickname(user.getBojId())
			.rank(count)
			.score(baekjoon.getScore())
			.avatarUrl(user.getImage())
			.rankUpDown(rank.getBojCurrentRank() - rank.getBojPreviousRank())

			.build();
	}
}

