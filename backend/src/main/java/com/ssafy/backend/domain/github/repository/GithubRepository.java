package com.ssafy.backend.domain.github.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.github.dto.FilteredGithubIdSet;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface GithubRepository extends JpaRepository<Github, Long> {
	Optional<Github> findByUserId(long userId);

	Optional<Github> findByUser(User user);

	@Query(
		"select count(g)"
			+ " from Github g"
			+ " where g.id in :githubIdSet"
			+ " and g.score > :score"
			+ " or g.score = :score"
			+ " and g.user.id < :userId"
	)
	int getRankWithFilter(Set<Long> githubIdSet, @Param("score") int score, @Param("userId") long userId);

	@Query("select count(g) from Github g where g.score > :score or g.score = :score and g.user.id < :userId")
	int getRank(@Param("score") int score, @Param("userId") long userId);

	List<Github> findAllByOrderByScoreDesc();
}
