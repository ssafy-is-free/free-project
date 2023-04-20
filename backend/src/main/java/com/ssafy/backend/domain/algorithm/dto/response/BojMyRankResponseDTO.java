package com.ssafy.backend.domain.algorithm.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BojMyRankResponseDTO {
    private Long userId;
    private String nickname;
    private Integer rank;
    private Integer score;
    private String avatarUrl;
    private Integer rankUpDown;
    private String tierUrl;

    @Builder
    public BojMyRankResponseDTO(Long userId, String nickname, Integer rank, Integer score, String avatarUrl, Integer rankUpDown, String tierUrl) {
        this.userId = userId;
        this.nickname = nickname;
        this.rank = rank;
        this.score = score;
        this.avatarUrl = avatarUrl;
        this.rankUpDown = rankUpDown;
        this.tierUrl = tierUrl;
    }



}
