package com.first.flash.account.member.application.dto;

import com.first.flash.account.member.domain.ContentType;
import com.first.flash.global.annotation.ValidEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record MemberReportRequest(@NotEmpty(message = "신고 사유는 필수입니다.") String reason,
                                  @NotNull(message = "콘텐츠 타입은 필수입니다.") @ValidEnum(enumClass = ContentType.class) ContentType contentType) {

}
