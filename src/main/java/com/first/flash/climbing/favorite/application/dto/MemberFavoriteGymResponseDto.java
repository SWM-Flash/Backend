package com.first.flash.climbing.favorite.application.dto;

public record MemberFavoriteGymResponseDto(String message) {

    public static MemberFavoriteGymResponseDto toDtoByStatus(final boolean present) {
        if (present) {
            return new MemberFavoriteGymResponseDto("즐겨찾기에서 제거되었습니다.");
        }
        return new MemberFavoriteGymResponseDto("즐겨찾기에 추가되었습니다.");
    }
}
