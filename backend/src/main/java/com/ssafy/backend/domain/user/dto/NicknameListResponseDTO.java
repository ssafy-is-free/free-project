package com.ssafy.backend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class NicknameListResponseDTO {

    private long userId;
    private String nickname;

    public static NicknameListResponseDTO create(long userId, String nickname) {
        return NicknameListResponseDTO.builder()
                .userId(userId)
                .nickname(nickname)
                .build();
    }
}
