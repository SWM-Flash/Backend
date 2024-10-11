package com.first.flash.climbing.solution.infrastructure;

import com.first.flash.climbing.solution.domain.SolutionComment;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionCommentRepository {

    SolutionComment save(final SolutionComment solutionComment);

    Optional<SolutionComment> findById(final Long id);

    List<SolutionComment> findBySolutionId(final Long solutionId);

    void deleteByCommenterId(final UUID commenterId);

    void updateCommenterInfo(final UUID commenterId, final String nickName, final String profileImageUrl);

    void delete(final SolutionComment comment);
}
