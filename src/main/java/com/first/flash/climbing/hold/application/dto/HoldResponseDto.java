package com.first.flash.climbing.hold.application.dto;

import com.first.flash.climbing.hold.domain.Hold;

public record HoldResponseDto(Long id, String colorName, String colorCode) {

    public static HoldResponseDto toDto(final Hold hold) {
        return new HoldResponseDto(hold.getId(), hold.getColorName(), hold.getColorCode());
    }
}
