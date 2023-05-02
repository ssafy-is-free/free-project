package com.ssafy.backend.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.backend.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByIdAndIsDeletedFalse(long userId);

	Optional<User> findByNicknameAndIsDeletedFalse(String nickname);

	Optional<User> findUserByBojIdAndIsDeletedFalse(String bojId);

	Optional<User> findByNickname(String nickname);
}
