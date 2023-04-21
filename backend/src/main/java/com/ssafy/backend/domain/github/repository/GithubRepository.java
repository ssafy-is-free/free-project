package com.ssafy.backend.domain.github.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.backend.domain.entity.Github;

@Repository
public interface GithubRepository extends JpaRepository<Github, Long> {

}
