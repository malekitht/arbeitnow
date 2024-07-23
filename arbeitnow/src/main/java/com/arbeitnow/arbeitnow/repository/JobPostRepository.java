package com.arbeitnow.arbeitnow.repository;

import com.arbeitnow.arbeitnow.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost, Integer> {
}
