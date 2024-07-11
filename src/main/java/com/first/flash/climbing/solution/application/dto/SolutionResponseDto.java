package com.first.flash.climbing.solution.application.dto;

import com.first.flash.climbing.solution.domain.Solution;

public record SolutionResponseDto(Long id, String uploader, String review, String instagramId,
                                  String videoUrl) {

    public static SolutionResponseDto toDto(Solution solution) {
        return new SolutionResponseDto(solution.getId(), solution.getUploader(),
            solution.getReview(), solution.getInstagramId(), solution.getVideoUrl());
    }
}
