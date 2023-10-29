package com.chop.subdata.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

// TODO: resolve bean conflict, WebClientConfig.java vs GithubWebClient.java
@Deprecated
//@Configuration
public class WebClientConfig {

  private static final String GITHUB_GRAPHQL = "https://api.github.com/graphql";

//  @Bean
  WebClient githubWebClient() {
    return WebClient.builder()
            .baseUrl(GITHUB_GRAPHQL)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
  }
}
