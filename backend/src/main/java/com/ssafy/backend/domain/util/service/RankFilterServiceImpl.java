package com.ssafy.backend.domain.util.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.common.LanguageType;
import com.ssafy.backend.domain.util.dto.LanguageResponse;
import com.ssafy.backend.domain.util.repository.LanguageQueryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RankFilterServiceImpl implements RankFilterService {

	private final LanguageQueryRepository languageQueryRepository;

	@Override
	public List<LanguageResponse> getLanguageList(String type) {

		LanguageType languageType = LanguageType.valueOf(type.toUpperCase());

		List<Language> languages = languageQueryRepository.findLanguageByType(languageType);

		return LanguageResponse.createList(languages);
	}
}
