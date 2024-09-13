package com.first.flash.account.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Member {

    @Id
    private UUID id;
    private String email;
    private String socialId;
    private String nickName;
    private String instagramId;
    private Double height;
    private Double reach;
    private String profileImageUrl;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Role role;

    public void completeRegistration(final String nickName, final String instagramId,
        final Double height, final Gender gender, final Double reach,
        final String profileImageUrl) {
        this.nickName = nickName;
        this.instagramId = instagramId;
        this.height = height;
        this.gender = gender;
        this.reach = reach;
        this.profileImageUrl = profileImageUrl;
    }

    public boolean hasSameNickName(final String nickName) {
        if (Objects.isNull(this.nickName)) {
            return false;
        }
        return this.nickName.equals(nickName);
    }

    public boolean isCompleteRegistration() {
        return Objects.nonNull(nickName);
    }
}
