package com.ssafy.backend.domain.util.service;

import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.common.LanguageType;
import com.ssafy.backend.domain.util.dto.LanguageDTO;
import com.ssafy.backend.domain.util.repository.LanguageRepositorySupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RankFilterServiceImpl implements RankFilterService{


    private final LanguageRepositorySupport languageRepositorySupport;

    @Override
    public List<LanguageDTO> getLanguageList(String type) {

        List<Language> languages = languageRepositorySupport.findLanguageByType(type);

        return languages.stream()
                .map(LanguageDTO::createDTO)
                .collect(Collectors.toList());

    }
}
