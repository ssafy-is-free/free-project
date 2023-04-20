package com.ssafy.backend.domain.algorithm.repository;

import com.ssafy.backend.domain.entity.BaekjoonLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BojLanguageRepository extends JpaRepository<BaekjoonLanguage, Long> {

}
