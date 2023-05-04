package com.ssafy.backend.domain.algorithm.dto.response;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import net.minidev.json.annotate.JsonIgnore;

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

	@JsonIgnore
	public boolean checkForNull() {
		return this.userId == null && this.nickname == null && this.rank == null &&
			this.score == null && this.avatarUrl == null && this.rankUpDown == null &&
			this.tierUrl == null;
	}

	public static BojRankResponse createEmpty() {
		return BojRankResponse.builder().build();
	}

	public static BojRankResponse createBojMyRankResponseDTO(Baekjoon baekjoon, User user, int rank) {
		if (baekjoon.getPreviousRank() == 0) {
			return BojRankResponse.builder()
				.userId(user.getId())
				.nickname(user.getBojId())
				.rank(rank)
				.score(baekjoon.getScore())
				.avatarUrl(user.getImage())
				.rankUpDown(0L)
				.tierUrl(baekjoon.getTier())
				.build();
		} else {
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
	}

	public static BojRankResponse create(Baekjoon baekjoon, int index, Long prevRank, Set<Long> baekjoonIdSet) {

		int returnScore = baekjoon.getScore() < 0 ?
			0 : baekjoon.getScore();

		long returnRank = prevRank == null ?
			0 : prevRank;

		//랭크 등락 폭
		long rankUpdate = baekjoonIdSet.isEmpty() ?
			0 : baekjoon.getPreviousRank() - returnRank;

		return BojRankResponse.builder()
			.userId(baekjoon.getUser().getId())
			.nickname(baekjoon.getUser().getBojId())
			.rank((int)returnRank + index)
			.score(returnScore)
			.avatarUrl(baekjoon.getUser().getImage())
			.rankUpDown(rankUpdate)
			.tierUrl(baekjoon.getTier())
			.build();
	}

	public static List<BojRankResponse> createList(List<Baekjoon> baekjoon, Long prevRank, Set<Long> baekjoonIdSet) {
		AtomicInteger index = new AtomicInteger(0);

		return baekjoon.stream()
			.map((b) -> BojRankResponse.create(b, index.incrementAndGet(), prevRank, baekjoonIdSet))
			.collect(Collectors.toList());

	}
}

