package com.ssafy.backend.domain.util.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.common.LanguageType;

public interface LanguageRepository extends JpaRepository<Language, Long> {

	Optional<Language> findByNameAndType(String name, LanguageType type);

	List<Language> findAllByType(LanguageType type);
}
