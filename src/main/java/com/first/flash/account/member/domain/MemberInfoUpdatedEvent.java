package com.first.flash.account.member.domain;

import com.first.flash.global.event.Event;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoUpdatedEvent extends Event {

    private final UUID memberId;
    private final String nickName;
    private final String instagramId;
    private final String profileImageUrl;
    private final Double height;
    private final Double reach;
    private final Gender gender;

    public static MemberInfoUpdatedEvent of(final UUID memberId, final String nickName,
        final String instagramId, final String profileImageUrl, final Double height,
        final Double reach, final Gender gender) {
        return new MemberInfoUpdatedEvent(memberId, nickName, instagramId, profileImageUrl, height,
            reach, gender);
    }
}
