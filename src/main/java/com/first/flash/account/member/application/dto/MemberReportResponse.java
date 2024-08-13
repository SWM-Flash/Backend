package com.first.flash.account.member.application.dto;

import com.first.flash.account.member.domain.Member;
import java.util.UUID;

public record MemberReportResponse(UUID reportedMemberId, String reportedMemberName,
                                   String reason) {

    public static MemberReportResponse toDto(final Member reportedMember, final String reason) {
        return new MemberReportResponse(reportedMember.getId(),
            reportedMember.getNickName(), reason);
    }
}
