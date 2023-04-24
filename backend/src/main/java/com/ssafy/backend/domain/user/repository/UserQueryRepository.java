package com.ssafy.backend.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.backend.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.backend.domain.entity.QUser.user;

@RequiredArgsConstructor
@Repository
public class UserQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<User> findByNickname(String nickname) {
        return queryFactory.selectFrom(user)
                .where(user.nickname.contains(nickname))
                .fetch();
    }
}
