package com.ssafy.backend.domain.algorithm.service;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.backend.domain.algorithm.repository.BojRepository;
import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.user.repository.UserRepository;

@SpringBootTest
public class BojMyRankServiceTest {
	@Autowired
	private BojRepository bojRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AlgorithmServiceImpl algorithmService;

	@AfterEach
	void tearDown() {
		bojRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("유저 아이디를 기반으로 해당 유저의 랭킹 정보를 반환하는 테스트")
	public void testGetBojByUserId() {
		//given
		User user1 = createUser("user1");
		User user2 = createUser("user2");
		User user3 = createUser("user3");

		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Baekjoon baekjoon = createBaekjoon();
		bojRepository.save(baekjoon);

		//when

		//then
	}

	private User createUser(String bojId) {
		return User.builder().bojId(bojId).image("1").isDeleted(false).build();
	}

	private Baekjoon createBaekjoon() {
		return Baekjoon.builder()
			.tier("https://d2gd6pc034wcta.cloudfront.net/tier/14.svg")
			.passCount(275)
			.tryFailCount(9)
			.submitCount(724)
			.failCount(173)
			.score(100)
			.build();
	}
}
