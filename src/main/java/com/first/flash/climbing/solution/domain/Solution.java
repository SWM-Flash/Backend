package com.first.flash.climbing.solution.domain;

import com.first.flash.account.member.domain.Gender;
import com.first.flash.climbing.solution.domain.vo.SolutionDetail;
import com.first.flash.climbing.solution.domain.vo.UploaderDetail;
import com.first.flash.global.domain.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
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
    @OneToMany(mappedBy = "solution", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<SolutionComment> comments = new ArrayList<>();

    protected Solution(final String uploader, final String review, final String instagramId,
        final String thumbnailImageUrl, final LocalDate solvedDate,
        final String videoUrl, final UUID problemId, final UUID uploaderId,
        final String profileImageUrl, final PerceivedDifficulty perceivedDifficulty,
        final Double uploaderHeight,
        final Double uploaderReach, final Gender uploaderGender) {

        this.solutionDetail = SolutionDetail.of(review, thumbnailImageUrl, videoUrl,
            solvedDate, perceivedDifficulty);
        this.uploaderDetail = UploaderDetail.of(uploaderId, uploader, instagramId, profileImageUrl,
            uploaderHeight, uploaderReach, uploaderGender);
        this.optionalWeight = DEFAULT_OPTIONAL_WEIGHT;
        this.problemId = problemId;
    }

    public static Solution of(final String uploader, final String review, final String instagramId,
        final String thumbnailImageUrl, final LocalDate solvedDate,
        final String videoUrl, final UUID problemId, final UUID uploaderId,
        final String profileImageUrl, final PerceivedDifficulty perceivedDifficulty,
        final Double uploaderHeight,
        final Double uploaderReach, final Gender uploaderGender) {

        return new Solution(uploader, review, instagramId, thumbnailImageUrl, solvedDate, videoUrl,
            problemId, uploaderId,
            profileImageUrl, perceivedDifficulty, uploaderHeight, uploaderReach, uploaderGender);
    }

    public void updateContentInfo(final String review, final String videoUrl, final String thumbnailImageUrl,
        final LocalDate solvedDate, final PerceivedDifficulty perceivedDifficulty) {
        this.solutionDetail = SolutionDetail.of(review, thumbnailImageUrl, videoUrl,
            solvedDate, perceivedDifficulty);
    }
}
