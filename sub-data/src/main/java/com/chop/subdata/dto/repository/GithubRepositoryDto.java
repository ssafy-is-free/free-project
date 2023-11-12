package com.chop.subdata.dto.repository;

import com.chop.subdata.dto.GithubLanguageDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class GithubRepositoryDto {
  private final List<OnlyGithubRepositoryDto> repositoryDtoList;
  private final List<GithubLanguageDto> languageDtoList;
}
