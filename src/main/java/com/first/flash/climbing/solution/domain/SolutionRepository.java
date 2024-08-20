package com.first.flash.climbing.solution.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SolutionRepository {

    Solution save(final Solution solution);

    Optional<Solution> findById(final Long id);

    List<Solution> findAllByProblemId(final UUID problemId);

    List<Solution> findAllByUploaderId(final UUID uploaderId);

    void deleteById(final Long id);
}
