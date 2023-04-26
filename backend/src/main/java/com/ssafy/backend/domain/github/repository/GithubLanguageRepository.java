package com.ssafy.backend.domain.github.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.backend.domain.entity.GithubLanguage;
import com.ssafy.backend.domain.github.repository.projection.GithubOnly;

public interface GithubLanguageRepository extends JpaRepository<GithubLanguage, Long> {

	List<GithubOnly> findByLanguageId(Long languageId);

}
