package com.first.flash.climbing.problem.infrastructure;

import com.first.flash.climbing.problem.domain.Problem;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemJpaRepository extends JpaRepository<Problem, UUID> {

    Problem save(final Problem problem);

    Optional<Problem> findById(final UUID id);

    void deleteById(final UUID id);
}
