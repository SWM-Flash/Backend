package com.first.flash.climbing.problem.infrastructure;

import com.first.flash.climbing.problem.domain.QueryProblem;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryProblemJpaRepository extends JpaRepository<QueryProblem, UUID> {

    QueryProblem save(final QueryProblem queryProblem);

    Optional<QueryProblem> findById(final UUID id);

    void deleteById(final UUID id);

    List<QueryProblem> findBySectorIdAndHoldIdAndDifficultyName(Long sectorId, Long holdId, String difficulty);
}
