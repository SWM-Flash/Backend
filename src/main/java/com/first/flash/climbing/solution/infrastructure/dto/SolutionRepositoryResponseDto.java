package com.first.flash.climbing.solution.infrastructure.dto;

import java.util.UUID;

public record SolutionRepositoryResponseDto(Long id, String uploader, String review,
                                            String instagramId, String videoUrl, UUID uploaderId,
                                            String profileImageUrl, Long commentCount) {

}
