package com.ssafy.backend.domain.user.repository;

import com.ssafy.backend.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByIdAndIsDeletedFalse(long userId);
    Optional<User> findByNicknameAndIsDeletedFalse(String nickname);
}
