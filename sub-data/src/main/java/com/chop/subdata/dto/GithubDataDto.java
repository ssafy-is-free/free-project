package com.chop.subdata.dto;

import com.chop.subdata.dto.repository.GithubRepositoryDto;
import com.chop.subdata.dto.userProfile.GithubUserProfileDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GithubDataDto {
  private final GithubUserProfileDto githubUserProfileDto;
  private final GithubRepositoryDto githubRepositoryDto;
}
