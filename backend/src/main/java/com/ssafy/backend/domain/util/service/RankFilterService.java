package com.ssafy.backend.domain.util.service;

import java.util.List;

import com.ssafy.backend.domain.util.dto.LanguageResponse;

public interface RankFilterService {

	List<LanguageResponse> getLanguageList(String type);
}
