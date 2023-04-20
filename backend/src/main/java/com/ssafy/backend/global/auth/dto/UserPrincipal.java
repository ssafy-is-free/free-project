package com.ssafy.backend.global.auth.dto;

import com.ssafy.backend.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

//로그인한 정보가 담긴 객체.
@Getter
public class UserPrincipal implements OAuth2User, UserDetails {

    private long id;
    private String email;

    @Builder
    public UserPrincipal(long id, String email) {
        this.id = id;
        this.email = email;
    }

    public static UserDetails createUserDetails(User user){

        return UserPrincipal.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

    @Override
    public String getPassword() {
        return null;
    }

    //이메일로 대체
    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    //id로 대체
    @Override
    public String getName() {
        return String.valueOf(this.id);
    }
}
