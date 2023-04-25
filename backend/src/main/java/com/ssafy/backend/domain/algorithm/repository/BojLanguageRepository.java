package com.ssafy.backend.domain.algorithm.repository;

import com.ssafy.backend.domain.entity.BaekjoonLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface BojLanguageRepository extends JpaRepository<BaekjoonLanguage, Long> {
    Optional<BaekjoonLanguage> findByLanguageIdAndBaekjoonId(Long languageId, Long baekjoonId);
    Optional<List<BaekjoonLanguage>> deleteAllByBaekjoonId(Long baekjoonId);

}
