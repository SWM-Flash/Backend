package com.first.flash.climbing.favorite.application.dto;

import com.first.flash.climbing.favorite.domain.MemberFavoriteGym;
import java.util.UUID;

public record MemberFavoriteGymResponseDto(Long id, Long gymId, UUID memberId) {

    public static MemberFavoriteGymResponseDto toDto(final MemberFavoriteGym memberFavoriteGym) {
        return new MemberFavoriteGymResponseDto(memberFavoriteGym.getId(),
            memberFavoriteGym.getGymId(), memberFavoriteGym.getMemberId());
    }
}
