package com.first.flash.climbing.solution.domain;

import com.first.flash.climbing.solution.infrastructure.dto.SolutionMetaData;
import java.util.UUID;

public interface SolutionMetaDataFetcher {

    Long getSolutionCountByGymInfoDifficultyName(final Long gymInfoId,
        final String difficultyName, final UUID memberId);

    SolutionMetaData getSolutionMetaData(final Long id);
}
