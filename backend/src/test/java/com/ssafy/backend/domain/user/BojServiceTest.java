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
import com.ssafy.backend.global.response.exception.CustomExceptionStatus;

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

	@AfterEach
	void tearDown() {
		languageRepository.deleteAllInBatch();
		bojLanguageRepository.deleteAllInBatch();
		bojRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("백준 아이디 크롤링 정상작동 테스트")
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
	@DisplayName("백준 리턴 값 테스트")
	public void testSaveBojIdCheckValue() {
		//given
		User user = createUser("user1", "test");
		userRepository.save(user);
		Language language1 = createLanguage("Pascal");
		languageRepository.saveAll(Arrays.asList(language1));
		//when
		bojService.saveId(user.getId(), "test");
		Baekjoon baekjoon = bojRepository.findByUser(user)
			.orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_BOJ_USER));
		// then
		assertThat(baekjoon).extracting(Baekjoon::getTier, Baekjoon::getPassCount, Baekjoon::getTryFailCount,
			Baekjoon::getSubmitCount, Baekjoon::getFailCount).containsExactly(0, 0, 1, 2, 1);

	}

	@Test
	@DisplayName("백준 닉네임 없음 테스트")
	public void testSaveBojIdNull() {
		//given
		User user = createUser("user1", "");
		userRepository.save(user);
		Language language1 = createLanguage("Java 11");
		Language language2 = createLanguage("C++17");
		Language language3 = createLanguage("Python3");
		Language language4 = createLanguage("Java 8");
		languageRepository.saveAll(Arrays.asList(language1, language2, language3, language4));
		//when	//then
		assertThatThrownBy(() -> bojService.saveId(user.getId(), "")).isInstanceOf(CustomException.class);

	}

	@Test
	@DisplayName("존재하지 않는 백준 닉네임 저장했을 때 테스트")
	public void testSaveWeirdBojId() {
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

	@Test
	@DisplayName("백준 ID로 유저 조회 실패")
	public void testCheckDuplicateId() {
		//given
		User user1 = createUser("user1", "user1");
		User user2 = createUser("user2", "user2");
		User user3 = createUser("user3", "user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		//when //then
		assertThatThrownBy(() -> bojService.checkDuplicateId("user1")).isInstanceOf(CustomException.class);
	}

	private User createUser(String nickname) {
		return User.builder().nickname(nickname).image("1").isDeleted(false).build();
	}

	private User createUser(String nickname, String bojId) {
		return User.builder().nickname(nickname).bojId(bojId).image("1").isDeleted(false).build();
	}

	private Language createLanguage(String name) {
		return Language.builder()
			.name(name)
			.type(LanguageType.BAEKJOON)
			.build();
	}
}
