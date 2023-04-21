package com.ssafy.backend.domain.user.repository;

import com.ssafy.backend.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
