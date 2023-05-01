package com.ssafy.backend.global.config.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "authorization-uri")
public class AuthorizeUrlProperties {

	private List<String> denyUrls;
}
