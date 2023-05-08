package com.ssafy.backend.domain.job.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.backend.domain.entity.JobHistory;
import com.ssafy.backend.domain.entity.JobPosting;

@Repository
public interface JobHistoryRepository extends JpaRepository<JobHistory, Long> {
	List<JobHistory> findByJobPosting(JobPosting jobPosting);

	Optional<JobHistory> findByIdAndIsDeletedFalse(long id);
}
