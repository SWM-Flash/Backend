package com.first.flash.climbing.problem.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProblemRepository {

    Problem save(final Problem problem);

    Optional<Problem> findById(final UUID id);

    void expireSectorsById(final List<Long> expiredSectorsIds);
}
