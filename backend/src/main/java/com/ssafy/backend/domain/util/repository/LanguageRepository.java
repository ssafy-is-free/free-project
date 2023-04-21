package com.ssafy.backend.domain.util.repository;

import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.common.LanguageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    Optional<Language> findByNameAndType(String name, LanguageType type);
}
