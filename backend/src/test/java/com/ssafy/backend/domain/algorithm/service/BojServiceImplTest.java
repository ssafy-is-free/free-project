/*
package com.ssafy.backend.domain.algorithm.service;

import static org.mockito.Mockito.*;

import java.util.Optional;

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
import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.job.repository.JobHistoryQueryRepository;
import com.ssafy.backend.domain.job.repository.JobHistoryRepository;
import com.ssafy.backend.domain.job.repository.JobPostingRepository;
import com.ssafy.backend.domain.user.repository.UserQueryRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.util.repository.LanguageRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("백준 유저 서비스")
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
	@Mock
	private JobPostingRepository jobPostingRepository;
	@Mock
	private JobHistoryRepository jobHistoryRepository;
	@Mock
	private JobHistoryQueryRepository jobHistoryQueryRepository;
	private AlgorithmServiceImpl algorithmService;

	@BeforeEach
	void setUp() {
		this.algorithmService = new AlgorithmServiceImpl(bojRepository, bojLanguageRepository, languageRepository,
			userRepository, userQueryRepository, bojQueryRepository, bojLanguageQueryRepository
			, jobPostingRepository, jobHistoryRepository, jobHistoryQueryRepository);
	}

	@Test
	@DisplayName("유저 아이디를 기반으로 해당 유저의 랭킹 정보를 반환하는 테스트")
	public void testGetBojByUserId() {
		//given
		User user = new User(1, "soda", "1", "sodamito2", "", false, "");
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		Baekjoon baekjoon = new Baekjoon(1, "https://d2gd6pc034wcta.cloudfront.net/tier/14.svg", 275, 9, 724, 173, 700,
			user, 0);
		when(bojRepository.findByUser(user)).thenReturn(Optional.of(baekjoon));

		//when

		//then
	}
}
*/
