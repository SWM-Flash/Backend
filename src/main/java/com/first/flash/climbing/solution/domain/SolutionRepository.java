package com.first.flash.climbing.solution.domain;

import java.util.List;
import java.util.Optional;

public interface SolutionRepository {

    Solution save(final Solution solution);

    Optional<Solution> findById(final Long id);

    List<Solution> findAllByProblemId(final Long problemId);
}
