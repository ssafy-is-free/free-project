package com.ssafy.backend.domain.github.repository;

import com.ssafy.backend.domain.entity.GithubLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GithubLanguageRepository extends JpaRepository<GithubLanguage, Long> {
}
