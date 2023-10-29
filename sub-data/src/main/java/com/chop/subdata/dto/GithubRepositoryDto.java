package com.chop.subdata.dto;

import lombok.Getter;

@Getter
public class GithubRepositoryDto {

    private String nameWithOwner;

    private String url;

    private Integer stargazerCount;

    private GithubBranch defaultBranchRef;

    private String readMe;

    public void setReadMe() {
        this.readMe = String.format("https://raw.githubusercontent.com/%s/%s/README.md", nameWithOwner, defaultBranchRef.getName());
    }
}
