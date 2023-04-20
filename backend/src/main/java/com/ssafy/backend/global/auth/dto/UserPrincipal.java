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

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
