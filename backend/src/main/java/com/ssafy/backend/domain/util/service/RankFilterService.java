package com.ssafy.backend.domain.util.service;

import java.util.List;

import com.ssafy.backend.domain.util.dto.LanguageDTO;

public interface RankFilterService {

	List<LanguageDTO> getLanguageList(String type);
}
