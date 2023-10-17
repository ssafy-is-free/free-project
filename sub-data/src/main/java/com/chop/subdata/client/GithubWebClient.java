package com.chop.subdata.client;

import com.fasterxml.jackson.databind.JsonNode;
import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class GithubWebClient {

  private final WebClient githubWebClient;

  public JsonNode getData(String accessToken, GraphQLRequest request) {
    return githubWebClient.post()
            .header("Authorization", "Bearer " + accessToken)
            .bodyValue(request.getRequestBody())
            .retrieve()
            .bodyToMono(JsonNode.class)
            .block();
  }
}
