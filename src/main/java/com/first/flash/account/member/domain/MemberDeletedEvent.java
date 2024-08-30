package com.first.flash.account.member.domain;

import com.first.flash.global.event.Event;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDeletedEvent extends Event {

    private final UUID memberId;

    public static MemberDeletedEvent of(final UUID memberId) {
        return new MemberDeletedEvent(memberId);
    }
}
