package com.first.flash.account.member.application;

import com.first.flash.account.member.application.dto.ConfirmNickNameRequest;
import com.first.flash.account.member.application.dto.ConfirmNickNameResponse;
import com.first.flash.account.member.application.dto.ManageConsentRequest;
import com.first.flash.account.member.application.dto.ManageConsentResponse;
import com.first.flash.account.member.application.dto.MemberCompleteRegistrationRequest;
import com.first.flash.account.member.application.dto.MemberCompleteRegistrationResponse;
import com.first.flash.account.member.application.dto.MemberInfoResponse;
import com.first.flash.account.member.domain.Member;
import com.first.flash.account.member.domain.MemberDeletedEvent;
import com.first.flash.account.member.domain.MemberInfoUpdatedEvent;
import com.first.flash.account.member.domain.MemberRepository;
import com.first.flash.account.member.exception.exceptions.MemberNotFoundException;
import com.first.flash.account.member.exception.exceptions.NickNameDuplicatedException;
import com.first.flash.global.event.Events;
import com.first.flash.global.util.AuthUtil;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(final Member member) {
        return memberRepository.save(member);
    }

    public Member findById(final UUID uuid) {
        return memberRepository.findById(uuid)
                               .orElseThrow(MemberNotFoundException::new);
    }

    @Transactional
    public MemberCompleteRegistrationResponse completeMemberRegistration(
        final MemberCompleteRegistrationRequest request) {
        UUID id = AuthUtil.getId();
        Member member = findById(id);
        if (!member.hasSameNickName(request.nickName()) &&
            memberRepository.existsByNickName(request.nickName())) {
            throw new NickNameDuplicatedException();
        }
        member.completeRegistration(request.nickName(), request.instagramId(), request.height(),
            request.gender(), request.reach(), request.profileImageUrl());

        Events.raise(MemberInfoUpdatedEvent.of(member.getId(), member.getNickName(),
            member.getInstagramId(), member.getProfileImageUrl(), member.getHeight(),
            member.getReach(), member.getGender()));

        return MemberCompleteRegistrationResponse.toDto(member);
    }

    public ConfirmNickNameResponse confirmNickName(final ConfirmNickNameRequest request) {
        boolean isDuplicated = memberRepository.existsByNickName(request.nickName());
        return ConfirmNickNameResponse.toDto(isDuplicated);
    }

    public MemberInfoResponse getMyInfo() {
        Member member = findMemberByAuthInfo();
        return MemberInfoResponse.toDto(member);
    }

    @Transactional
    public MemberInfoResponse deleteMember() {
        Member member = findMemberByAuthInfo();
        memberRepository.deleteById(member.getId());
        Events.raise(MemberDeletedEvent.of(member.getId()));
        return MemberInfoResponse.toDto(member);
    }

    @Transactional
    public ManageConsentResponse manageMarketingConsent(final ManageConsentRequest request) {
        Boolean hasAgreedToMarketing = request.hasAgreedToMarketing();
        Member member = findMemberByAuthInfo();
        member.manageMarketingConsent(hasAgreedToMarketing);
        return ManageConsentResponse.toDto(member);
    }

    private Member findMemberByAuthInfo() {
        UUID id = AuthUtil.getId();
        return findById(id);
    }
}
