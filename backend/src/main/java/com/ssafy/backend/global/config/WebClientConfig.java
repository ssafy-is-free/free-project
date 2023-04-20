package com.ssafy.backend.global.config;

import com.ssafy.backend.global.config.properties.ExternalServiceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder, ExternalServiceProperties externalServiceProperties){
        return webClientBuilder.baseUrl(externalServiceProperties.getUrl()).build();
    }
}
