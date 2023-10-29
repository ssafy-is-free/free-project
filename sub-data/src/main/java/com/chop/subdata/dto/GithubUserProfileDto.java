package com.chop.subdata.dto;

import lombok.Getter;

@Getter
public class GithubUserProfileDto {

    private String url;

    private Followers followers;

    private String avatarUrl;

    private String login;
}
