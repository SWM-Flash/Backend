package com.first.flash.climbing.solution.domain.vo;

import com.first.flash.account.member.domain.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class UploaderDetail {

    @Column(columnDefinition = "BINARY(16)")
    private UUID uploaderId;
    private String uploader;
    private String instagramId;
    private String profileImageUrl;
    private Double height;
    private Double reach;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    protected UploaderDetail(final UUID uploaderId, final String uploader,
        final String instagramId, final String profileImageUrl, final Double height,
        final Double reach,
        final Gender gender) {
        this.uploaderId = uploaderId;
        this.uploader = uploader;
        this.instagramId = instagramId;
        this.profileImageUrl = profileImageUrl;
        this.height = height;
        this.reach = reach;
        this.gender = gender;
    }

    public static UploaderDetail of(final UUID uploaderId, final String uploader,
        final String instagramId, final String profileImageUrl, final Double height,
        final Double reach, final Gender gender) {
        return new UploaderDetail(uploaderId, uploader, instagramId, profileImageUrl, height, reach,
            gender);
    }
}
