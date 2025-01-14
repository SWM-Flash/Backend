package com.first.flash.climbing.solution.domain;

import com.first.flash.account.member.domain.Gender;
import com.first.flash.climbing.solution.application.dto.MySolutionFilter;
import com.first.flash.climbing.solution.infrastructure.dto.MemberSolutionGroupDto;
import com.first.flash.climbing.solution.infrastructure.dto.DetailSolutionDto;
import com.first.flash.climbing.solution.infrastructure.dto.SolutionRepositoryResponseDto;
import com.first.flash.climbing.solution.infrastructure.paging.SolutionCursor;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SolutionRepository {

    Solution save(final Solution solution);

    Optional<Solution> findById(final Long id);

    List<SolutionRepositoryResponseDto> findAllByProblemId(final UUID problemId,
        final List<UUID> blockedMembers);

    void deleteById(final Long id);

    void updateUploaderInfo(final UUID uploaderId, final String nickName, final String instagramId,
        final String profileImageUrl, final Double uploaderHeight,
        final Double uploaderReach, final Gender uploaderGender);

    List<DetailSolutionDto> findDetailSolutionGroupById(final UUID uploaderId, final Long gymId, final LocalDate solvedDate);

    void deleteByUploaderId(final UUID memberId);

    List<MemberSolutionGroupDto> findMySolutions(final UUID myId,
        final MySolutionFilter mySolutionFilter, final SolutionCursor prevSolutionCursor,
        final int size);
}
