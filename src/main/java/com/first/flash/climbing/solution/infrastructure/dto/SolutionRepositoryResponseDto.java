package com.first.flash.climbing.solution.infrastructure.dto;

import com.first.flash.account.member.domain.Gender;
import java.util.UUID;

public record SolutionRepositoryResponseDto(Long id, String uploader, String review,
                                            String instagramId, String videoUrl, UUID uploaderId,
                                            Double uploaderHeight, Double uploaderReach,
                                            Gender uploaderGender, String profileImageUrl,
                                            Long commentCount) {

}
