package com.ssafy.backend.domain.github.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CGithubDTO {

    private String nickname;

    private String profileLink;

    private String avatarUrl;

    private int followers;

    private int commit;

    private int star;

    private List<CGithubRepoDTO> repositories;

    private List<CGithubLanguageDTO> languages;
}
