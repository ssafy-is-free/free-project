package com.ssafy.backend.domain.github.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.github.repository.querydsl.GithubRepositoryCustom;

@Repository
public interface GithubRepository extends JpaRepository<Github, Long>, GithubRepositoryCustom {
	Optional<Github> findByUserId(long userId);

	Optional<Github> findByUser(User user);

}
