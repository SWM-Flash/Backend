package com.first.flash.climbing.solution.application.dto;

import com.first.flash.climbing.solution.domain.Solution;
import com.first.flash.climbing.solution.domain.vo.SolutionDetail;
import com.first.flash.climbing.solution.domain.vo.UploaderDetail;
import com.first.flash.global.util.AuthUtil;
import java.util.UUID;

public record SolutionWriteResponseDto(Long id, String uploader, String review, String instagramId,
                                       String videoUrl, UUID uploaderId, Boolean isUploader,
                                       String profileImageUrl) {

    public static SolutionWriteResponseDto toDto(final Solution solution) {
        SolutionDetail solutionDetail = solution.getSolutionDetail();
        UploaderDetail uploaderDetail = solution.getUploaderDetail();
        UUID uploaderId = uploaderDetail.getUploaderId();
        Boolean isUploader = AuthUtil.isSameId(uploaderId);

        return new SolutionWriteResponseDto(solution.getId(), uploaderDetail.getUploader(),
            solutionDetail.getReview(), uploaderDetail.getInstagramId(),
            solutionDetail.getVideoUrl(), uploaderDetail.getUploaderId(), isUploader,
            uploaderDetail.getProfileImageUrl());
    }
}
