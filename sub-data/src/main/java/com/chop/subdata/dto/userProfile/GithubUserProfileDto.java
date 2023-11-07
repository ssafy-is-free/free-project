package com.chop.subdata.dto.userProfile;

import com.chop.subdata.dto.userProfile.FollowersDto;
import lombok.Getter;

@Getter
public class GithubUserProfileDto {

    private String url;

    private FollowersDto followers;

    private String avatarUrl;

    private String login;
}
