package com.ssafy.backend.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AccessTokenResponseDTO {

    @JsonProperty("access-token")
    private String accessToken;


    public static AccessTokenResponseDTO createDTO(String accessToken){

        return AccessTokenResponseDTO.builder()
                .accessToken(accessToken)
                .build();
    }
}
