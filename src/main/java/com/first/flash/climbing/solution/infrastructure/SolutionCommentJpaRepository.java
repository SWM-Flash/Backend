package com.first.flash.climbing.solution.infrastructure;

import com.first.flash.climbing.solution.domain.SolutionComment;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolutionCommentJpaRepository extends JpaRepository<SolutionComment, Long> {

    SolutionComment save(final SolutionComment solutionComment);

    List<SolutionComment> findBySolutionId(final Long solutionId);

    void deleteByCommenterDetailCommenterId(final UUID commenterId);

    void delete(final SolutionComment comment);
}
