package com.ssafy.backend.domain.github.repository.querydsl;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ssafy.backend.domain.entity.Github;

public interface GithubRepositoryCustom {

	List<Github> findAll(Long githubId, Integer score, Pageable pageable);
}
