package com.ssafy.backend.domain.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.backend.domain.entity.JobStatus;

public interface JobStatusRepository extends JpaRepository<JobStatus, Long> {

}
