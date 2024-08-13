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

    private final ReportRepository repository;
    private final MemberService memberService;

    @Transactional
    public MemberReportResponse reportMember(final UUID reportedUserId, final MemberReportRequest request) {
        UUID reporterId = AuthUtil.getId();
        Member reporter = memberService.findById(reporterId);
        Member reportedMember = memberService.findById(reportedUserId);
        MemberReport memberReport = MemberReport.reportMember(request.reason(), reporter, reportedMember);
        repository.save(memberReport);
        return MemberReportResponse.toDto(reportedMember, request.reason());
    }
}
