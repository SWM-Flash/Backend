package com.first.flash.account.member.application.dto;

import com.first.flash.account.member.domain.ContentType;

public record MemberReportResponse(Long reportedContentId, String contentType, String reason) {

    public static MemberReportResponse toDto(final Long reportedContentId,
        final ContentType contentType, final String reason) {
        return new MemberReportResponse(reportedContentId, contentType.name(), reason);
    }
}
