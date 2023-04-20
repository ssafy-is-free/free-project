package com.ssafy.backend.domain.algorithm.repository;

import com.ssafy.backend.domain.entity.Baekjoon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BojRepository extends JpaRepository<Baekjoon, Long> {
}
