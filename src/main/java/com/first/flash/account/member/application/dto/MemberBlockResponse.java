package com.first.flash.account.member.application.dto;

import com.first.flash.account.member.domain.Member;
import java.util.UUID;

public record MemberBlockResponse(UUID blockedMemberId, String blockedMemberName) {

    public static MemberBlockResponse toDto(final Member blockedMember) {
        return new MemberBlockResponse(blockedMember.getId(), blockedMember.getNickName());
    }
}
