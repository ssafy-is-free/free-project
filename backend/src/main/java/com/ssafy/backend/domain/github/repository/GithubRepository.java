package com.ssafy.backend.domain.github.repository;

import com.ssafy.backend.domain.entity.Github;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GithubRepository extends JpaRepository<Github, Long> {

    Optional<Github> findByUserId(long userId);
}
