package com.ssafy.backend.domain.user.repository;

import com.ssafy.backend.domain.entity.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Rank,Long> {

}
