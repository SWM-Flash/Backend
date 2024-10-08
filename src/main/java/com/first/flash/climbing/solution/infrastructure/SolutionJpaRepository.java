package com.first.flash.climbing.solution.infrastructure;

import com.first.flash.climbing.solution.domain.Solution;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolutionJpaRepository extends JpaRepository<Solution, Long> {

    Solution save(final Solution solution);

    Optional<Solution> findById(final Long id);

    List<Solution> findByProblemId(final UUID problemId);

    List<Solution> findByUploaderDetail_UploaderId(final UUID uploaderId);

    void deleteById(final Long id);

    void deleteByUploaderDetail_UploaderId(final UUID memberId);
}
