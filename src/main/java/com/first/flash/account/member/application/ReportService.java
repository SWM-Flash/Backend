package com.first.flash.account.member.application;

import com.first.flash.account.member.application.dto.MemberReportRequest;
import com.first.flash.account.member.application.dto.MemberReportResponse;
import com.first.flash.account.member.domain.Member;
import com.first.flash.account.member.domain.MemberReport;
import com.first.flash.account.member.infrastructure.ReportRepository;
import com.first.flash.global.util.AuthUtil;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;
    private final MemberService memberService;

    @Transactional
    public MemberReportResponse reportMember(final Long reportedContentId,
        final MemberReportRequest request) {
        UUID reporterId = AuthUtil.getId();
        Member reporter = memberService.findById(reporterId);
        MemberReport memberReport = MemberReport.reportContent(request.reason(), reporter,
            reportedContentId, request.contentType());
        MemberReport savedReport = reportRepository.save(memberReport);
        return MemberReportResponse.toDto(savedReport.getReportedContentId(),
            savedReport.getContentType(), savedReport.getReason());
    }
}
