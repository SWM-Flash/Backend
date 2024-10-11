package com.first.flash.climbing.solution.infrastructure.dto;

import java.util.UUID;

public record SolutionResponseDto(Long id, String uploader, String review, String instagramId,
                                  String videoUrl, UUID uploaderId, Boolean isUploader,
                                  String profileImageUrl, Long commentCount) {

}
