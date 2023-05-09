package com.ssafy.backend.domain.user.repository;

import static com.ssafy.backend.domain.entity.QUser.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserQueryRepository {

	private final JPAQueryFactory queryFactory;

	public List<User> findByNickname(String nickname) {
		return queryFactory.selectFrom(user)
			.where(user.nickname.contains(nickname))
			.fetch();
	}

	public List<User> findByBojId(String bojId) {
		return queryFactory.selectFrom(user)
			.where(user.bojId.contains(bojId), user.isDeleted.eq(false))
			.fetch();
	}
}
