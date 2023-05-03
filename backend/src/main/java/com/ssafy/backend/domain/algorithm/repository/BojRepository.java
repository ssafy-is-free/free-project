package com.ssafy.backend.domain.algorithm.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.User;
import org.springframework.data.jpa.repository.Query;

public interface BojRepository extends JpaRepository<Baekjoon, Long> {
	Optional<Baekjoon> findByUser(User user);

	List<Baekjoon> findAllByOrderByScoreDesc();

	@Query(
			"select count(g)"
					+ " from Baekjoon g"
					+ " where g.id in :bojIdSet"
					+ " and g.score > :score"
					+ " or g.score = :score"
					+ " and g.user.id < :userId"
	)
	int getRankWithFilter(Set<Long> bojIdSet, @Param("score") int score, @Param("userId") long userId);

	@Query("select count(g) from Baekjoon g where g.score > :score or g.score = :score and g.user.id < :userId")
	int getRank(@Param("score") int score, @Param("userId") long userId);
}
