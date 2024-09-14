package com.first.flash.global.filter.handler.dto;

import com.first.flash.account.member.domain.Role;
import com.first.flash.global.util.AuthUtil;

public record DeniedExceptionDto(String reason) {

    public static DeniedExceptionDto createDefault() {
        if (AuthUtil.hasRole(Role.ROLE_UNREGISTERED_USER.name())) {
            return new DeniedExceptionDto("UNREGISTERED_USER");
        }
        return new DeniedExceptionDto("NOT_ALLOWED");
    }
}
