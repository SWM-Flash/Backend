package com.first.flash.climbing.problem.domain;

import com.first.flash.climbing.problem.infrastructure.dto.ThumbnailSolutionDto;
import com.first.flash.climbing.solution.domain.Solution;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProblemRepository {

    Problem save(final Problem problem);

    Optional<Problem> findById(final UUID id);

    void expireProblemsBySectorIds(final List<Long> expiredSectorsIds);

    void deleteByProblemId(final UUID problemId);

    ThumbnailSolutionDto findNextSolution(final UUID problemId);
}
