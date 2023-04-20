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

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getProfileImage() {
        return null;
    }

    @Override
    public String getGender() {
        return null;
    }

    @Override
    public String getAge() {
        return null;
    }
}
