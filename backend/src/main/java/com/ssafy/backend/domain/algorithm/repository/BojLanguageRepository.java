package com.ssafy.backend.domain.algorithm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.backend.domain.entity.BaekjoonLanguage;

public interface BojLanguageRepository extends JpaRepository<BaekjoonLanguage, Long> {
	Optional<BaekjoonLanguage> findByLanguageIdAndBaekjoonId(Long languageId, Long baekjoonId);

	List<BaekjoonLanguage> deleteAllByBaekjoonId(Long baekjoonId);

	List<BaekjoonLanguage> findAllById(long id);
}
