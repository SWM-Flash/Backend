package com.first.flash.account.member.application.dto;

import jakarta.validation.constraints.NotEmpty;

public record MemberReportRequest(@NotEmpty(message = "신고 사유는 필수입니다.") String reason) {

}
