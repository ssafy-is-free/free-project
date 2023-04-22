package com.ssafy.backend.global.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;


/*여러 소셜에서 정보를 담을 수 있도록 추상화해서 사용.*/
@Getter
@AllArgsConstructor
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public abstract String getUser();
    public abstract String getName(); //이름 또는 닉네임
    public abstract String getProfileImage(); //프로필 이미지
}
