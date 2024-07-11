package com.first.flash.climbing.solution.domain;

import com.first.flash.climbing.solution.domain.dto.SolutionCreateRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Solution {

    private static final Long DEFAULT_OPTIONAL_WEIGHT = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uploader;
    private String review;
    private String instagramId;
    private String videoUrl;
    private Long optionalWeight;
    private Long problemId;

    protected Solution(String uploader, String review, String instagramId, String videoUrl,
        long problemId) {
        
        this.uploader = uploader;
        this.review = review;
        this.instagramId = instagramId;
        this.videoUrl = videoUrl;
        this.optionalWeight = DEFAULT_OPTIONAL_WEIGHT;
        this.problemId = problemId;
    }

    public static Solution of(final SolutionCreateRequestDto createRequestDto,
        final Long problemId) {

        return new Solution(createRequestDto.uploader(), createRequestDto.review(),
            createRequestDto.instagramId(), createRequestDto.videoUrl(), problemId);
    }
}
