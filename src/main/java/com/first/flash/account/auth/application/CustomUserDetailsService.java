package com.first.flash.account.auth.application;

import com.first.flash.account.auth.application.dto.CustomUserDetails;
import com.first.flash.account.member.application.MemberService;
import com.first.flash.account.member.domain.Member;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(final String id) throws UsernameNotFoundException {
        Member foundMember = memberService.findById(UUID.fromString(id));
        return new CustomUserDetails(foundMember.getId(), foundMember.getRole(),
            foundMember.getNickName());
    }
}
