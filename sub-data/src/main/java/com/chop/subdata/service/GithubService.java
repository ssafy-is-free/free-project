package com.chop.subdata.service;

import com.chop.subdata.client.GithubWebClient;
import com.chop.subdata.dto.repository.GithubRepositoryDto;
import com.chop.subdata.dto.userProfile.GithubUserProfileDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GithubService {

    private final GithubWebClient githubWebClient;
    private final ObjectMapper objectMapper;

    public void getGithubData(String accessToken) throws JsonProcessingException {
        GithubUserProfileDto userProfile = getUserProfile(accessToken);

        getRepositories(accessToken);

        // TODO getRepositories 구현 완성 후 응답 형식 지정 필요
    }

    private void getRepositories(String accessToken) throws JsonProcessingException {
        String query = loadGraphqlQuery("repository.graphql");
        Map<String, Object> variables = new HashMap<>();
        JsonNode response;
        long totalSize = 0;
        List<GithubRepositoryDto> githubRepositoryDtoList = new ArrayList<>();
        HashMap<String, Long> languageSizeMap = new HashMap<>();
        do {
            GraphQLRequest request = GraphQLRequest.builder().query(query).variables(variables).build();
            response = githubWebClient.getData(accessToken, request);
            if (response.isNull()) {
                return;
            }
            JsonNode data = response.get("data").get("viewer").get("repositories").get("nodes");
            for (JsonNode repo : data) {
                if (repo.get("isPrivate").booleanValue()) {
                    continue;
                }

                GithubRepositoryDto githubRepositoryDto = objectMapper.treeToValue(repo, GithubRepositoryDto.class);
                githubRepositoryDto.setReadMe();

                githubRepositoryDtoList.add(githubRepositoryDto);

                for (JsonNode language : repo.get("languages").get("edges")) {
                    long size = language.get("size").asLong();
                    String name = language.get("node").get("name").asText();

                    if (languageSizeMap.containsKey(name)) {
                        languageSizeMap.put(name, languageSizeMap.get(name) + size);
                    } else {
                        languageSizeMap.put(name, size);
                    }
                    totalSize += size;
                }
            }
            variables.put("cursor", response.get("data").get("viewer").get("repositories").get("pageInfo").get("endCursor"));
        } while (response.get("data").get("viewer").get("repositories").get("pageInfo").get("hasNextPage").booleanValue());

        // TODO 언어 비율 계산, 응답 형식 확인
    }

    private GithubUserProfileDto getUserProfile(String accessToken) throws JsonProcessingException {
        String query = loadGraphqlQuery("user-profile.graphql");

        GraphQLRequest body = GraphQLRequest.builder()
                .query(query)
                .build();

        JsonNode data = githubWebClient.getData(accessToken, body)
                .get("data")
                .get("viewer");

        return objectMapper.treeToValue(data, GithubUserProfileDto.class);
    }

    private String loadGraphqlQuery(String fileName) {
        ClassPathResource resource = new ClassPathResource("graphql/" + fileName);
        try {
            InputStream inputStream = resource.getInputStream();
            String query = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            log.info("Successfully load graphql query. query name: {}", fileName);

            return query;
        } catch (IOException e) {
            log.error("Failed to load graphql query. query name: {}", fileName);
            throw new RuntimeException("Failed to load GraphQL query from file: " + fileName, e);
        }
    }
}
