package com.first.flash.account.member.application.dto;

import com.first.flash.account.member.domain.Gender;
import com.first.flash.account.member.domain.Member;

public record MemberInfoResponse(String nickName, String instagramId, Double height, Double reach,
                                 String profileImageUrl, Gender gender) {

    public static MemberInfoResponse toDto(final Member member) {
        return new MemberInfoResponse(
            member.getNickName(), member.getInstagramId(),
            member.getHeight(), member.getReach(), member.getProfileImageUrl(), member.getGender());
    }
}
