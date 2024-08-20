package com.first.flash.climbing.solution.application.dto;

import com.first.flash.climbing.solution.domain.Solution;
import com.first.flash.climbing.solution.domain.vo.SolutionDetail;
import com.first.flash.climbing.solution.domain.vo.UploaderDetail;

public record SolutionResponseDto(Long id, String uploader, String review, String instagramId,
                                  String videoUrl) {

    public static SolutionResponseDto toDto(final Solution solution) {
        SolutionDetail solutionDetail = solution.getSolutionDetail();
        UploaderDetail uploaderDetail = solution.getUploaderDetail();

        return new SolutionResponseDto(solution.getId(), uploaderDetail.getUploader(),
            solutionDetail.getReview(), uploaderDetail.getInstagramId(),
            solutionDetail.getVideoUrl());
    }
}
