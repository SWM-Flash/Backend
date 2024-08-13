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

@Entity
@Getter
@NoArgsConstructor
public class MemberReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reason;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "reporterId")
    private Member reporter;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "reportedId")
    private Member reported;

    private MemberReport(final String reason, final Member reporter, final Member reported) {
        this.reason = reason;
        this.reporter = reporter;
        this.reported = reported;
    }

    public static MemberReport reportMember(final String reason, final Member reporter,
        final Member reported) {
        return new MemberReport(reason, reporter, reported);
    }
}
