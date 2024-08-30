package com.first.flash.climbing.solution.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class UploaderDetail {

    @Column(columnDefinition = "BINARY(16)")
    private UUID uploaderId;
    private String uploader;
    private String instagramId;

    protected UploaderDetail(final UUID uploaderId, final String uploader,
        final String instagramId) {
        this.uploaderId = uploaderId;
        this.uploader = uploader;
        this.instagramId = instagramId;
    }

    public static UploaderDetail of(final UUID uploaderId, final String uploader,
        final String instagramId) {
        return new UploaderDetail(uploaderId, uploader, instagramId);
    }

    public void updateInfo(final String uploader, final String instagramId) {
        this.uploader = uploader;
        this.instagramId = instagramId;
    }
}
