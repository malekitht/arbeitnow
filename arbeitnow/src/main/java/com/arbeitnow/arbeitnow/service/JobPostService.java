package com.arbeitnow.arbeitnow.service;

import com.arbeitnow.arbeitnow.entity.JobPost;
import com.arbeitnow.arbeitnow.repository.JobPostRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobPostService {

    @Autowired
    private JobPostRepository jobPostRepository;

    public List<JobPost> getAllJobPosts() {
        return jobPostRepository.findAll();
    }

    public Optional<JobPost> getJobPostById(Integer id) {
        return jobPostRepository.findById(id);
    }

    public JobPost createJobPost(JobPost jobPost) {
        return jobPostRepository.save(jobPost);
    }

    public void deleteJobPostById(Integer id) {
        jobPostRepository.deleteById(id);
    }

    public List<Map.Entry<String, Long>> getTop10CitiesByJobCount() {
        return jobPostRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(JobPost::getLocation, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public Map<String, Long> getCityStatistics() {
        return jobPostRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(JobPost::getLocation, Collectors.counting()));
    }

    public JobPost updateJobPost(Integer id, JobPost jobPostDetails) {
        return jobPostRepository.findById(id).map(jobPost -> {
            BeanUtils.copyProperties(jobPostDetails, jobPost, "id");
            return jobPostRepository.save(jobPost);
        }).orElseThrow(() -> new RuntimeException("JobPost not found with id " + id));
    }
}