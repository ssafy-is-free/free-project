package com.ssafy.backend.domain.algorithm.dto.response;

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


}
