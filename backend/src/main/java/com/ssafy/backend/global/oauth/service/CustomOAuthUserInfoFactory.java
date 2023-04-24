package com.ssafy.backend.global.oauth.service;

import com.ssafy.backend.global.oauth.dto.GithubOAuth2UserInfo;
import com.ssafy.backend.global.oauth.dto.OAuth2UserInfo;
import com.ssafy.backend.global.oauth.exception.OAuth2AuthenticationProcessingException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomOAuthUserInfoFactory {

    //받아온 데이터를 각 소셜에 맞게 객체와 매핑 시켜줌.
    public OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) throws OAuth2AuthenticationProcessingException {
        if(registrationId.equalsIgnoreCase(AuthProvider.github.toString())){
            return new GithubOAuth2UserInfo(attributes);
        }
        else{
            throw new OAuth2AuthenticationProcessingException("Unsupported Login Type : " + registrationId);
        }

    }
}
