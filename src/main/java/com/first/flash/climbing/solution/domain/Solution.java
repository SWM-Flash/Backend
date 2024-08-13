package com.first.flash.climbing.solution.domain;

import com.first.flash.climbing.solution.domain.vo.SolutionDetail;
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
public class Solution {

    private static final Long DEFAULT_OPTIONAL_WEIGHT = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    SolutionDetail solutionDetail;
    private Long optionalWeight;
    @Column(columnDefinition = "BINARY(16)")
    private UUID problemId;
    @Column(columnDefinition = "BINARY(16)")
    private UUID memberId;

    protected Solution(final String uploader, final String review, final String instagramId,
        final String videoUrl, final UUID problemId, final UUID memberId) {

        this.solutionDetail = SolutionDetail.of(uploader, review, instagramId, videoUrl);
        this.optionalWeight = DEFAULT_OPTIONAL_WEIGHT;
        this.problemId = problemId;
        this.memberId = memberId;
    }

    public static Solution of(final String uploader, final String review, final String instagramId,
        final String videoUrl,
        final UUID problemId, final UUID memberId) {

        return new Solution(uploader, review, instagramId, videoUrl, problemId, memberId);
    }

    public void updateMemberInfo(final String uploader, final String instagramId) {
        this.solutionDetail.updateMemberInfo(uploader, instagramId);
    }

    public void updateContentInfo(final String review, final String videoUrl) {
        this.solutionDetail.updateContentInfo(review, videoUrl);
    }
}
