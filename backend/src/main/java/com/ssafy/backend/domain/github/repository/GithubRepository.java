package com.ssafy.backend.domain.github.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
	)
	int getRankWithFilter(Set<Long> githubIdSet, @Param("score") int score);

	@Query("select count(g) from Github g where g.score > :score")
	int getRank(int score);
}
