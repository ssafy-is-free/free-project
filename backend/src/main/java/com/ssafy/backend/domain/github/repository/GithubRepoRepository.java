package com.ssafy.backend.domain.github.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.backend.domain.entity.GithubRepo;

public interface GithubRepoRepository extends JpaRepository<GithubRepo, Long> {

}
