package com.ssafy.backend.domain.job.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.backend.domain.entity.JobPosting;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

	Optional<JobPosting> findByName(String name);

	Optional<JobPosting> findByIdAndIsCloseFalse(long jobPostingId);

}
