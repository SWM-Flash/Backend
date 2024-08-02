package com.first.flash.account.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
        final String profileImageUrl, final Gender gender) {
        return Member.builder()
                     .id(id)
                     .email(email)
                     .nickName(nickName)
                     .instagramId(instagramId)
                     .height(height)
                     .reach(reach)
                     .profileImageUrl(profileImageUrl)
                     .gender(gender)
                     .role(Role.ROLE_USER)
                     .build();
    }
}
