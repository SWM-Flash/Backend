package com.first.flash.account.member.application.dto;

import com.first.flash.account.member.domain.Member;

public record MemberCompleteRegistrationResponse(String nickName, String instagramId, Double height,
                                                 Double reach, String profileImageUrl) {

    public static MemberCompleteRegistrationResponse toDto(final Member member) {
        return new MemberCompleteRegistrationResponse(
            member.getNickName(), member.getInstagramId(),
            member.getHeight(), member.getReach(), member.getProfileImageUrl());
    }
}
