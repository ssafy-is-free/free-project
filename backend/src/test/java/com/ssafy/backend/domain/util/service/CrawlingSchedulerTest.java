package com.ssafy.backend.domain.util.service;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.backend.domain.algorithm.repository.BojLanguageRepository;
import com.ssafy.backend.domain.algorithm.repository.BojRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.util.repository.LanguageRepository;

@SpringBootTest
public class CrawlingSchedulerTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BojRepository bojRepository;
	@Autowired
	private LanguageRepository languageRepository;
	@Autowired
	private BojLanguageRepository bojLanguageRepository;
	@Autowired
	private CrawlingScheduler crawlingScheduler;

	@AfterEach
	void tearDown() {
		languageRepository.deleteAllInBatch();
		bojLanguageRepository.deleteAllInBatch();
		bojRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();
	}

	/*@Test
	@DisplayName("백준 스케줄링 테스트")
	public void bojUpdateTest() {
		//given

		//when
		crawlingScheduler.bojUpdate();

		//then
	}*/
}
