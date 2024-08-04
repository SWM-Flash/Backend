package com.first.flash.account.member.application;

import com.first.flash.account.member.domain.Member;
import com.first.flash.account.member.domain.MemberRepository;
import com.first.flash.account.member.exception.exceptions.MemberNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(final Member member) {
        return memberRepository.save(member);
    }

    public Member findById(final UUID uuid) {
        return memberRepository.findById(uuid)
                               .orElseThrow(MemberNotFoundException::new);
    }
}
