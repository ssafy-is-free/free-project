package com.ssafy.backend.domain.algorithm.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BojLanguageResultDTO {
    @JsonProperty("language")
    private String language;

    @JsonProperty("pass_percentage")
    private String passPercentage;

    @JsonProperty("pass_count")
    private int passCount;
}
