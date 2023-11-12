package com.chop.subdata.dto.repository;

import lombok.Getter;

@Getter
public class OnlyGithubRepositoryDto {

    private String nameWithOwner;

    private String url;

    private Integer stargazerCount;

    private GithubBranchDto defaultBranchRef;

    private String readMe;

    public void setReadMe() {
        this.readMe = String.format("https://raw.githubusercontent.com/%s/%s/README.md", nameWithOwner, defaultBranchRef.getName());
    }
}
