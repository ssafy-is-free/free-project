package com.ssafy.backend.domain.user.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.TestConfig;
import com.ssafy.backend.domain.entity.User;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("유저 레포지토리 queryDsl 테스트")
@Import(TestConfig.class)
class UserQueryRepositoryTest {

	private UserQueryRepository userQueryRepository;
	@Autowired
	private UserRepository userRepository;

	private JPAQueryFactory queryFactory;

	@Autowired
	private TestEntityManager testEntityManager;
	private EntityManager entityManager;

	@BeforeEach
	void setup() {
		entityManager = testEntityManager.getEntityManager();
		queryFactory = new JPAQueryFactory(entityManager);

		userQueryRepository = new UserQueryRepository(queryFactory);

		//테스트용 데이터 저장.
		List<User> userList = new ArrayList<>();
		userList.add(createUser("nickname1", "image1", "bojId1", false));
		userList.add(createUser("nickname2", "image2", "bojId2", false));
		userList.add(createUser("nickname3", "image3", "bojId3", false));
		userList.add(createUser("nickname4", "image4", "bojId4", false));

		userRepository.saveAll(userList);
	}

	@AfterEach
	void tearDown() {
		userRepository.deleteAllInBatch();

		this.entityManager
			.createNativeQuery(
				"ALTER TABLE users ALTER COLUMN `id` RESTART                                                                     WITH 1")
			.executeUpdate();
	}

	@Test
	@DisplayName("해당 닉네임이 포함된 유저 조회 - 조회된 값이 중간에 있는 값일때,")
	void userNicknameContainTest1() {
		//given
		String nickname = "ickna";

		//when
		List<User> userList = userQueryRepository.findByNickname(nickname);

		//then
		Assertions.assertThat(userList)
			.isNotEmpty()
			.hasSize(4)
			.extracting("nickname").contains("nickname1", "nickname2", "nickname3", "nickname4");

	}

	@Test
	@DisplayName("해당 닉네임이 포함된 유저 조회 - 조회된 값이 시작값일때")
	void userNicknameContainTest2() {
		//given
		String nickname = "ame1";

		//when

		//then

	}

	@Test
	@DisplayName("해당 닉네임이 포함된 유저 조회 - 조회된 값이 끝 값일때,")
	void userNicknameContainTest3() {
		//given
		String nickname = "nick";

		//when

		//then

	}

	@Test
	@DisplayName("해당 닉네임이 포함된 유저 조회 - 조회 결과가 없을때")
	void userNicknameContainTest4() {
		//given
		String nickname = "qwefasd";

		//when

		//then

	}

	public User createUser(String nickname, String image, String bojId, boolean isDeleted) {

		return User.builder()
			.nickname(nickname)
			.image(image)
			.bojId(bojId)
			.isDeleted(isDeleted)
			.build();
	}
}