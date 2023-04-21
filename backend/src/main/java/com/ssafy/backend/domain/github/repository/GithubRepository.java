package com.ssafy.backend.domain.github.repository;

import com.ssafy.backend.domain.entity.Github;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GithubRepository extends JpaRepository<Github, Long> {
}
