package com.ssafy.backend.domain.util.repository;

import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.common.LanguageType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    Language findByNameAndType(String name, LanguageType type);
}
