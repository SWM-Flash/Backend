package com.first.flash.account.member.infrastructure;

import com.first.flash.account.member.domain.MemberReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<MemberReport, Long> {

    MemberReport save(final MemberReport memberReport);
}
