package com.ssafy.backend.domain.algorithm.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.ssafy.backend.domain.algorithm.repository.BojLanguageQueryRepository;
import com.ssafy.backend.domain.algorithm.repository.BojLanguageRepository;
import com.ssafy.backend.domain.algorithm.repository.BojQueryRepository;
import com.ssafy.backend.domain.algorithm.repository.BojRepository;
import com.ssafy.backend.domain.user.repository.UserQueryRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.util.repository.LanguageRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("랭킹 필터 서비스 테스트")
public class BojServiceImplTest {
	@Mock
	private BojRepository bojRepository;
	@Mock
	private BojLanguageRepository bojLanguageRepository;
	@Mock
	private LanguageRepository languageRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private WebClient webClient;
	@Mock
	private UserQueryRepository userQueryRepository;
	@Mock
	private BojQueryRepository bojQueryRepository;
	@Mock
	private BojLanguageQueryRepository bojLanguageQueryRepository;
	private AlgorithmServiceImpl algorithmService;

	@BeforeEach
	void setUp() {
		this.algorithmService = new AlgorithmServiceImpl(bojRepository, bojLanguageRepository, languageRepository,
			userRepository, webClient, userQueryRepository, bojQueryRepository, bojLanguageQueryRepository);
	}

	@Test
	@DisplayName("유저 아이디를 기반으로 해당 유저의 랭킹 정보를 반환하는 테스트")
	public void testGetBojByUserId() {
		//given

		//when

		//then
	}
}
