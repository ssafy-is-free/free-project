package com.ssafy.backend.domain.github.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.User;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface GithubRepository extends JpaRepository<Github, Long> {
	Optional<Github> findByUserId(long userId);

	Optional<Github> findByUser(User user);

	List<Github> findByUserIdIn(Set<Long> userId);

	@Query("select count(g) from Github g where g.score > :score or g.score = :score and g.user.id < :userId")
	Long getRank(@Param("score") int score, @Param("userId") long userId);

	List<Github> findAllByOrderByScoreDesc();
}
