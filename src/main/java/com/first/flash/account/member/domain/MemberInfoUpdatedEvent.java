package com.first.flash.account.member.domain;

import com.first.flash.global.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoUpdatedEvent extends Event {

    private final Member member;

    public static MemberInfoUpdatedEvent of(Member member) {
        return new MemberInfoUpdatedEvent(member);
    }
}
