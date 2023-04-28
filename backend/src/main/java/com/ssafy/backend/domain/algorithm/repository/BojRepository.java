package com.ssafy.backend.domain.algorithm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.User;

public interface BojRepository extends JpaRepository<Baekjoon, Long> {
	Optional<Baekjoon> findByUser(User user);

	List<Baekjoon> findAllByOrderByScoreDesc();
}
