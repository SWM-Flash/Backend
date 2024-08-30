package com.first.flash.climbing.solution.domain;

import com.first.flash.climbing.solution.domain.vo.SolutionDetail;
import com.first.flash.climbing.solution.domain.vo.UploaderDetail;
import com.first.flash.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Solution extends BaseEntity {

    private static final Long DEFAULT_OPTIONAL_WEIGHT = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "BINARY(16)")
    private UUID problemId;
    private SolutionDetail solutionDetail;
    private UploaderDetail uploaderDetail;
    private Long optionalWeight;

    protected Solution(final String uploader, final String review, final String instagramId,
        final String videoUrl, final UUID problemId, final UUID uploaderId) {

        this.solutionDetail = SolutionDetail.of(review, videoUrl);
        this.uploaderDetail = UploaderDetail.of(uploaderId, uploader, instagramId);
        this.optionalWeight = DEFAULT_OPTIONAL_WEIGHT;
        this.problemId = problemId;
    }

    public static Solution of(final String uploader, final String review, final String instagramId,
        final String videoUrl,
        final UUID problemId, final UUID uploaderId) {

        return new Solution(uploader, review, instagramId, videoUrl, problemId, uploaderId);
    }

    public void updateUploaderInfo(final String uploader, final String instagramId) {
        UUID uploaderId = this.uploaderDetail.getUploaderId();

        this.uploaderDetail = UploaderDetail.of(uploaderId, uploader, instagramId);
    }

    public void updateContentInfo(final String review, final String videoUrl) {
        this.solutionDetail = SolutionDetail.of(review, videoUrl);
    }
}
