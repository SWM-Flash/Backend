package com.first.flash.climbing.solution.domain;

import com.first.flash.climbing.solution.domain.dto.SolutionCreateRequestDto;
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

    protected Solution(final String uploader, final String review, final String instagramId,
        final String videoUrl, final UUID problemId) {

        this.solutionDetail = SolutionDetail.of(uploader, review, instagramId, videoUrl);
        this.optionalWeight = DEFAULT_OPTIONAL_WEIGHT;
        this.problemId = problemId;
    }

    public static Solution of(final SolutionCreateRequestDto createRequestDto,
        final UUID problemId) {

        return new Solution(createRequestDto.uploader(), createRequestDto.review(),
            createRequestDto.instagramId(), createRequestDto.videoUrl(), problemId);
    }
}
