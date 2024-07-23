package com.arbeitnow.arbeitnow.controller;

import com.arbeitnow.arbeitnow.entity.JobPost;
import com.arbeitnow.arbeitnow.service.JobPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/job")
public class JobPostController {

    @Autowired
    private JobPostService jobPostService;

    @GetMapping
    public List<JobPost> getAllJobPosts() {
        return jobPostService.getAllJobPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobPost> getJobPostById(@PathVariable Integer id) {
        return jobPostService.getJobPostById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public JobPost createJobPost(@RequestBody JobPost jobPost) {
        return jobPostService.createJobPost(jobPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobPostById(@PathVariable Integer id) {
        jobPostService.deleteJobPostById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobPost> updateJobPost(@PathVariable Integer id, @RequestBody JobPost jobPostDetails) {
        try {
            JobPost updatedJobPost = jobPostService.updateJobPost(id, jobPostDetails);
            return ResponseEntity.ok(updatedJobPost);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/top10cities")
    public ResponseEntity<List<Map.Entry<String, Long>>> getTop10CitiesByJobCount() {
        List<Map.Entry<String, Long>> topCities = jobPostService.getTop10CitiesByJobCount();
        return ResponseEntity.ok(topCities);
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getCityStatistics() {
        Map<String, Long> stats = jobPostService.getCityStatistics();
        return ResponseEntity.ok(stats);
    }
}