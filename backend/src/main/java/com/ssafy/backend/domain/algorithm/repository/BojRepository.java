package com.ssafy.backend.domain.algorithm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.backend.domain.entity.Baekjoon;

public interface BojRepository extends JpaRepository<Baekjoon, Long> {
	Optional<Baekjoon> findByUserId(Long userId);

	List<Baekjoon> findAllByOrderByScoreDesc();
}
