package com.first.flash.climbing.solution.domain.dto;

import com.first.flash.climbing.solution.domain.Solution;

public record SolutionResponseDto(String uploader, String review, String instagramId,
                                  String videoUrl) {

    public static SolutionResponseDto toDto(Solution solution) {
        return new SolutionResponseDto(solution.getUploader(), solution.getReview(),
            solution.getInstagramId(), solution.getVideoUrl());
    }
}
