package com.ssafy.backend.domain.github.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GitHubRankingFilter {
    private Long languageId;

    private Long jobPostingId;

    public boolean isNull() {
        return languageId == null && jobPostingId == null;
    }

}
