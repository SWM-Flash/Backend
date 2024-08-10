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

@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
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

    public static Member of(final UUID id, final String email, final String nickName,
        final String instagramId, final Double height, final Double reach,
        final String profileImageUrl, final Gender gender, final String socialId) {
        return Member.builder()
                     .id(id)
                     .email(email)
                     .nickName(nickName)
                     .socialId(socialId)
                     .instagramId(instagramId)
                     .height(height)
                     .reach(reach)
                     .profileImageUrl(profileImageUrl)
                     .gender(gender)
                     .role(Role.ROLE_USER)
                     .build();
    }

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
}
