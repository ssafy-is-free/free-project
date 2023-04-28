package com.ssafy.backend.domain.algorithm.dto.response;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// TODO: 2023-04-28 유저테이블의 식별자를 Long로 설정했기 때문에 rank 또한 long이 되어야 함.
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BojRankResponse {
	private Long userId;
	private String nickname;
	private Integer rank;
	private Integer score;
	private String avatarUrl;
	private Long rankUpDown;
	private String tierUrl;

	public static BojRankResponse createBojMyRankResponseDTO(Baekjoon baekjoon, User user, int rank) {
		return BojRankResponse.builder()
			.userId(user.getId())
			.nickname(user.getBojId())
			.rank(rank)
			.score(baekjoon.getScore())
			.avatarUrl(user.getImage())
			.rankUpDown(baekjoon.getPreviousRank() - rank)
			.tierUrl(baekjoon.getTier())
			.build();
	}

	public static BojRankResponse create(Baekjoon baekjoon, int index, long rank) {

		int returnScore = baekjoon.getScore() < 0 ?
			0 : baekjoon.getScore();

		return BojRankResponse.builder()
			.userId(baekjoon.getUser().getId())
			.nickname(baekjoon.getUser().getBojId())
			.rank((int)rank + index)
			.score(returnScore)
			.avatarUrl(baekjoon.getUser().getImage())
			.rankUpDown(baekjoon.getPreviousRank() - rank)
			.tierUrl(baekjoon.getTier())
			.build();
	}

	public static List<BojRankResponse> createList(List<Baekjoon> baekjoon, Long rank) {
		AtomicInteger index = new AtomicInteger(0);

		// TODO: 2023-04-28 좀 더 간단하게 처리 할 수 있을듯.
		return rank == null ?
			baekjoon.stream()
				.map((b) -> BojRankResponse.create(b, index.incrementAndGet(), 0L))
				.collect(Collectors.toList()) :
			baekjoon.stream()
				.map((b) -> BojRankResponse.create(b, index.incrementAndGet(), rank))
				.collect(Collectors.toList());

	}
}

