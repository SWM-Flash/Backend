package com.first.flash.climbing.solution.infrastructure;

import com.first.flash.climbing.solution.domain.SolutionComment;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionCommentRepository {

    SolutionComment save(final SolutionComment solutionComment);

    List<SolutionComment> findBySolutionId(Long solutionId);
}
