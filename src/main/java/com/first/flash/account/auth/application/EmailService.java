package com.first.flash.account.auth.application;

import com.first.flash.account.auth.domain.EmailProvider;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final Map<String, EmailProvider> providers;


}
