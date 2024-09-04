package com.first.flash.account.member.domain;

import com.first.flash.global.domain.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class MemberReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reason;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "reporterId")
    private Member reporter;
    private Long reportedContentId;

    private MemberReport(final String reason, final Member reporter, final Long reportedContentId) {
        this.reason = reason;
        this.reporter = reporter;
        this.reportedContentId = reportedContentId;
    }

    public static MemberReport reportContent(final String reason, final Member reporter,
        final Long reportedContentId) {
        return new MemberReport(reason, reporter, reportedContentId);
    }
}
