package com.ssafy.backend.domain.github.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.GithubRepo;

public interface GithubRepoRepository extends JpaRepository<GithubRepo, Long> {

	long countByGithubIdIn(Set<Long> githubId);

	long countByGithub(Github github);
}
