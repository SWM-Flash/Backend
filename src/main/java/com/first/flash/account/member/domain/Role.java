package com.first.flash.account.member.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {

    ROLE_ADMIN("ROLE_ADMIN"), ROLE_USER("ROLE_USER");

    private String role;
}
