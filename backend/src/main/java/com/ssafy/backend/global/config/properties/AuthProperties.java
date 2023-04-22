package com.ssafy.backend.global.config.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    private String accessTokenSecret;
    private String refreshTokenSecret;
    private String redirectPage;
}
