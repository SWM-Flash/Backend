package com.first.flash.climbing.solution.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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

    protected UploaderDetail(final UUID uploaderId, final String uploader,
        final String instagramId, final String profileImageUrl) {
        this.uploaderId = uploaderId;
        this.uploader = uploader;
        this.instagramId = instagramId;
        this.profileImageUrl = profileImageUrl;
    }

    public static UploaderDetail of(final UUID uploaderId, final String uploader,
        final String instagramId, final String profileImageUrl) {
        return new UploaderDetail(uploaderId, uploader, instagramId, profileImageUrl);
    }
}
