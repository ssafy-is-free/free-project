package com.ssafy.backend.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	private final long MAX_AGE_SECS = 3600; //1시간

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/**")
			.allowedOriginPatterns("*")//외부에서 들어오는 모든 url 허용
			.allowedMethods("GET", "POST", "PATCH", "DELETE") //허용되는 메서드
			.allowedHeaders("*")//허용되는 헤더 지원
			.allowCredentials(true) //자격증명(쿠피요청을 허용 - 다른 도메인 서버에 인증하는 경우에만 사용)
			.maxAge(MAX_AGE_SECS);
	}
}
