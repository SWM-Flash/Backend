package com.first.flash.account.auth.application;

import static com.first.flash.account.member.domain.Role.ROLE_USER;

import com.first.flash.account.auth.application.dto.LoginRequestDto;
import com.first.flash.account.auth.application.dto.LoginResponseDto;
import com.first.flash.account.auth.domain.TokenManager;
import com.first.flash.account.member.domain.Member;
import com.first.flash.account.member.domain.MemberRepository;
import com.first.flash.account.member.domain.Role;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthService {

    private static final Role DEFAULT_ROLE = ROLE_USER;

    private final MemberRepository memberRepository;
    private final TokenManager tokenManager;

    @Transactional
    public LoginResponseDto login(final LoginRequestDto request) {
        log.info("로그인 요청: {}", request);
        Optional<Member> foundMember = memberRepository.findByEmail(request.email());
        Member member = saveOrGetMember(request, foundMember);
        String accessToken = tokenManager.createAccessToken(member.getId());
        return new LoginResponseDto(accessToken);
    }

    private Member saveOrGetMember(final LoginRequestDto request,
        final Optional<Member> foundMember) {
        if (foundMember.isPresent()) {
            return foundMember.get();
        }
        Member member = Member.builder()
                              .id(UUID.randomUUID())
                              .email(request.email())
                              .role(DEFAULT_ROLE)
                              .build();
        memberRepository.save(member);
        return member;
    }
}
