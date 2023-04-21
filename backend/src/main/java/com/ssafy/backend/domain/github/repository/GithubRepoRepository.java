package com.ssafy.backend.domain.github.repository;

import com.ssafy.backend.domain.entity.GithubRepo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GithubRepoRepository extends JpaRepository<GithubRepo, Long> {
}
