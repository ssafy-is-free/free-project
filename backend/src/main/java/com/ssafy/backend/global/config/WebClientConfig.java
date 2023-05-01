package com.ssafy.backend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.ssafy.backend.global.config.properties.ExternalServiceProperties;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient webClient(WebClient.Builder webClientBuilder,
		ExternalServiceProperties externalServiceProperties) {
		return webClientBuilder.baseUrl(externalServiceProperties.getUrl()).build();
	}
}
