package com.first.flash.account.member.application.dto;

public record MemberReportResponse(Long reportedContentId, String reason) {

    public static MemberReportResponse toDto(final Long reportedContentId, final String reason) {
        return new MemberReportResponse(reportedContentId, reason);
    }
}
