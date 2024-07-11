package com.first.flash.climbing.solution.infrastructure;

import com.first.flash.climbing.solution.domain.Solution;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolutionJpaRepository extends JpaRepository<Solution, Long> {

    Solution save(final Solution solution);

    Optional<Solution> findById(final long id);

    List<Solution> findByProblemId(Long problemId);
}
