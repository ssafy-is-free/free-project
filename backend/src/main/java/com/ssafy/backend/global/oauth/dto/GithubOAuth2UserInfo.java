package com.ssafy.backend.global.oauth.dto;

import java.util.Map;

public class GithubOAuth2UserInfo extends OAuth2UserInfo{


    public GithubOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getUser() {
        return attributes.toString();
    }

    //닉네임.
    @Override
    public String getName() {
        return attributes.get("login").toString();
    }

    @Override
    public String getProfileImage() {
        return attributes.get("avatar_url").toString();
    }

}
