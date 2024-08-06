package com.first.flash.account.auth.infrastructure;

import com.first.flash.account.auth.domain.EmailProvider;
import org.springframework.stereotype.Component;

@Component
public class GoogleEmailProvider implements EmailProvider {

    @Override
    public String getEmail(final String token) {
        return null;
    }
}
