package com.first.flash.climbing.solution.application.dto;

import com.first.flash.climbing.solution.domain.Solution;
import com.first.flash.climbing.solution.domain.vo.SolutionDetail;

public record SolutionResponseDto(Long id, String uploader, String review, String instagramId,
                                  String videoUrl) {

    public static SolutionResponseDto toDto(final Solution solution) {
        SolutionDetail detail = solution.getSolutionDetail();

        return new SolutionResponseDto(solution.getId(), detail.getUploader(), detail.getReview(),
            detail.getInstagramId(), detail.getVideoUrl());
    }
}
