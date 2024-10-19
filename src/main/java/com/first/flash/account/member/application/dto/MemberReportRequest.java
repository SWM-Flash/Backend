package com.first.flash.account.member.application.dto;

import com.first.flash.account.member.domain.ContentType;
import com.first.flash.global.annotation.ValidEnum;
import jakarta.validation.constraints.NotEmpty;

public record MemberReportRequest(@NotEmpty(message = "신고 사유는 필수입니다.") String reason,
                                  @ValidEnum(enumClass = ContentType.class) ContentType contentType) {

    public MemberReportRequest(final String reason, final ContentType contentType) {
        this.reason = reason;
        this.contentType = contentType != null ? contentType : ContentType.SOLUTION;
    }
}
