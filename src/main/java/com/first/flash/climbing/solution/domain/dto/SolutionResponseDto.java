package com.first.flash.climbing.solution.domain.dto;

import com.first.flash.climbing.solution.infrastructure.dto.SolutionRepositoryResponseDto;
import com.first.flash.global.util.AuthUtil;
import java.util.UUID;

public record SolutionResponseDto(Long id, String uploader, String review, String instagramId,
                                  String videoUrl, UUID uploaderId, Boolean isUploader,
                                  String profileImageUrl, Long commentCount) {

    public static SolutionResponseDto from(
        final SolutionRepositoryResponseDto repositoryResponseDto) {
        return new SolutionResponseDto(repositoryResponseDto.id(), repositoryResponseDto.uploader(),
            repositoryResponseDto.review(), repositoryResponseDto.instagramId(),
            repositoryResponseDto.videoUrl(), repositoryResponseDto.uploaderId(),
            AuthUtil.isSameId(repositoryResponseDto.uploaderId()),
            repositoryResponseDto.profileImageUrl(), repositoryResponseDto.commentCount());
    }
}
