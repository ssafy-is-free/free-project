package com.chop.subdata.client;

import com.fasterxml.jackson.databind.JsonNode;
import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GithubWebClient {
  private static final String GITHUB_GRAPHQL = "https://api.github.com/graphql";
  private final WebClient webClient;

  public GithubWebClient() {
    webClient = WebClient.builder()
            .baseUrl(GITHUB_GRAPHQL)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
  }

  public JsonNode getData(String accessToken, GraphQLRequest request) {
    return webClient.post()
            .header("Authorization", "Bearer " + accessToken)
            .bodyValue(request.getRequestBody())
            .retrieve()
            .bodyToMono(JsonNode.class)
            .block();
  }
}
