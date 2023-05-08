package com.ssafy.backend.domain.user;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import com.ssafy.backend.domain.algorithm.repository.BojLanguageRepository;
import com.ssafy.backend.domain.algorithm.repository.BojRepository;
import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.entity.common.LanguageType;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.user.service.BojService;
import com.ssafy.backend.domain.util.repository.LanguageRepository;
import com.ssafy.backend.global.response.exception.CustomException;

@SpringBootTest
public class BojServiceTest {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BojRepository bojRepository;
	@Autowired
	private LanguageRepository languageRepository;
	@Autowired
	private BojLanguageRepository bojLanguageRepository;
	@Autowired
	private BojService bojService;
	@Autowired
	private WebClient webClient;

	@AfterEach
	void tearDown() {
		languageRepository.deleteAllInBatch();
		bojLanguageRepository.deleteAllInBatch();
		bojRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();
		webClient.delete();
	}

	@Test
	@DisplayName("백준 아이디를 저장했을 때 크롤링하는 테스트")
	public void testSaveBojIdSuccess() {
		//given
		User user = createUser("user1");
		userRepository.save(user);
		Language language1 = createLanguage("Java 11");
		Language language2 = createLanguage("C++17");
		Language language3 = createLanguage("Python3");
		Language language4 = createLanguage("Java 8");
		languageRepository.saveAll(Arrays.asList(language1, language2, language3, language4));
		//when
		bojService.saveId(user.getId(), "sodamito2");
		//then
		Optional<Baekjoon> oBaekjoon = bojRepository.findById(user.getId());
		Assert.assertNotNull(oBaekjoon);

	}

	@Test
	@DisplayName("없는 백준 아이디 저장했을 때 테스트")
	public void testSaveBojIdFail() {
		//given
		User user = createUser("user1");
		userRepository.save(user);
		Language language1 = createLanguage("Java 11");
		Language language2 = createLanguage("C++17");
		Language language3 = createLanguage("Python3");
		Language language4 = createLanguage("Java 8");
		languageRepository.saveAll(Arrays.asList(language1, language2, language3, language4));
		//when	//then
		assertThatThrownBy(() -> bojService.saveId(user.getId(), "sodami")).isInstanceOf(CustomException.class);

	}

	private User createUser(String nickname) {
		return User.builder().nickname(nickname).image("1").isDeleted(false).build();
	}

	private Language createLanguage(String name) {
		return Language.builder()
			.name(name)
			.type(LanguageType.BAEKJOON)
			.build();
	}
}
