package com.arbeitnow.arbeitnow.entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Boolean remote;

    private String url;

    @ElementCollection
    private List<String> tags;

    @ElementCollection
    private List<String> jobTypes;

    private String location;

    private Long createdAt;

    public JobPost(String slug1, String company1, String title1, String description1, boolean b, String location1, long l) {
        this.slug = slug1;
        this.companyName = company1;
        this.title = title1;
        this.description = description1;
        this.remote = b;
        this.location = location1;
        this.createdAt = l;
    }
}