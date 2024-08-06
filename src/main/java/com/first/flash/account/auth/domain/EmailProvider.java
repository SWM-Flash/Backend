package com.first.flash.account.auth.domain;

public interface EmailProvider {

    String getEmail(final String token);
}
