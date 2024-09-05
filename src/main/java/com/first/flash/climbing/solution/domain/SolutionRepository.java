package com.first.flash.climbing.solution.domain;

import com.first.flash.climbing.solution.infrastructure.dto.MySolutionDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SolutionRepository {

    Solution save(final Solution solution);

    Optional<Solution> findById(final Long id);

    List<Solution> findAllByProblemId(final UUID problemId, final List<UUID> blockedMembers);

    List<MySolutionDto> findAllByUploaderId(final UUID uploaderId);

    void deleteById(final Long id);

    void updateUploaderInfo(final UUID uploaderId, final String nickName, final String instagramId);
}
