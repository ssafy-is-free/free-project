package com.ssafy.backend.domain.github.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.backend.domain.entity.GithubLanguage;
import com.ssafy.backend.domain.github.repository.projection.GithubId;

public interface GithubLanguageRepository extends JpaRepository<GithubLanguage, Long> {

	List<GithubId> findByLanguageIdIn(List<Long> list);

}
