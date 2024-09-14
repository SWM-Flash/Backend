package com.first.flash.account.member.domain;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public enum Role {

    ROLE_ADMIN("ROLE_ADMIN"), ROLE_USER("ROLE_USER"),
    ROLE_WEB("ROLE_WEB"), ROLE_UNREGISTERED_USER("ROLE_UNREGISTERED_USER");

    private String role;

    public boolean isUnregisteredUser() {
        return this.equals(ROLE_UNREGISTERED_USER);
    }
}
