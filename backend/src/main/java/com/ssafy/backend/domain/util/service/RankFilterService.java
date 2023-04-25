package com.ssafy.backend.domain.util.service;

import com.ssafy.backend.domain.entity.common.LanguageType;
import com.ssafy.backend.domain.util.dto.LanguageDTO;

import java.util.List;

public interface RankFilterService {


    List<LanguageDTO> getLanguageList(String type);
}
