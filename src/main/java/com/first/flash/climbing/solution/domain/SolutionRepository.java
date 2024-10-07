package com.first.flash.climbing.solution.domain;

import com.first.flash.climbing.solution.infrastructure.dto.DetailSolutionDto;
import com.first.flash.climbing.solution.infrastructure.dto.MySolutionDto;
import com.first.flash.climbing.solution.infrastructure.paging.SolutionCursor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SolutionRepository {

    Solution save(final Solution solution);

    Optional<Solution> findById(final Long id);

    List<Solution> findAllByProblemId(final UUID problemId, final List<UUID> blockedMembers);

    void deleteById(final Long id);

    void updateUploaderInfo(final UUID uploaderId, final String nickName, final String instagramId,
        final String profileImageUrl);

    DetailSolutionDto findDetailSolutionById(final Long solutionId);

    void deleteByUploaderId(final UUID memberId);

    List<MySolutionDto> findMySolutions(final UUID myId, final SolutionCursor prevSolutionCursor,
        final int size, final Long gymId, final List<String> difficulty);
}
