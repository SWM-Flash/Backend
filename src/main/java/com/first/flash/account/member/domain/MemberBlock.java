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
public class MemberBlock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "blockerId")
    private Member blocker;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "blockedId")
    private Member blocked;

    private MemberBlock(final Member blocker, final Member blocked) {
        this.blocker = blocker;
        this.blocked = blocked;
    }

    public static MemberBlock blockMember(final Member blocker, final Member blocked) {
        return new MemberBlock(blocker, blocked);
    }
}
