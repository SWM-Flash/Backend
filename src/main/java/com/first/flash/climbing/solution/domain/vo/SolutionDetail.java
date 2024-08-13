package com.first.flash.climbing.solution.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class SolutionDetail {

    private String uploader;
    private String review;
    private String instagramId;
    private String videoUrl;

    protected SolutionDetail(final String uploader, final String review, final String instagramId,
        final String videoUrl) {

        this.uploader = uploader;
        this.review = review;
        this.instagramId = instagramId;
        this.videoUrl = videoUrl;
    }

    public static SolutionDetail of(final String uploader, final String review,
        final String instagramId, final String videoUrl) {

        return new SolutionDetail(uploader, review, instagramId, videoUrl);
    }

    public void updateMemberInfo(final String uploader, final String instagramId) {
        this.uploader = uploader;
        this.instagramId = instagramId;
    }

    public void updateContentInfo(final String review, final String videoUrl) {
        this.review = review;
        this.videoUrl = videoUrl;
    }
}
