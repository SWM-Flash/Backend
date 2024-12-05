package com.first.flash.climbing.problem.application;

import com.first.flash.climbing.problem.application.dto.ProblemDetailResponseDto;
import com.first.flash.climbing.problem.domain.Problem;
import com.first.flash.climbing.problem.domain.ProblemRepository;
import com.first.flash.climbing.problem.domain.QueryProblem;
import com.first.flash.climbing.problem.domain.QueryProblemRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemsService {

    private final QueryProblemRepository queryProblemRepository;
    private final ProblemRepository problemRepository;
    private final ProblemReadService problemReadService;

    @Transactional
    public void changeRemovalDate(final Long sectorId, final LocalDate removalDate, final boolean isExpired) {
        queryProblemRepository.updateRemovalDateBySectorId(sectorId, removalDate, isExpired);
    }

    @Transactional
    public void expireProblems(final List<Long> expiredSectorsIds) {
        queryProblemRepository.expireProblemsBySectorIds(expiredSectorsIds);
        problemRepository.expireProblemsBySectorIds(expiredSectorsIds);
    }
    
    @Transactional
    public void changeThumbnailInfo(final UUID problemId, final Long solutionId, 
        final String thumbnailImageUrl, final String uploader) {
        Problem problem = problemReadService.findProblemById(problemId);
        QueryProblem queryProblem = problemReadService.findQueryProblemById(problemId);

        if (queryProblem.getHasSolution()) {
            return;
        }

        problem.setThumbnailInfo(solutionId, thumbnailImageUrl, uploader);
        queryProblem.setThumbnailInfo(solutionId, thumbnailImageUrl, uploader);
    }

    @Transactional
    public void updateProblemSolutionInfo(final UUID problemId) {
        QueryProblem queryProblem = problemReadService.findQueryProblemById(problemId);
        queryProblem.addSolutionCount();
    }

    @Transactional
    public void updateProblemDeletedSolutionInfo(final UUID problemId,
        final Integer perceivedDifficulty) {
        QueryProblem queryProblem = problemReadService.findQueryProblemById(problemId);
        queryProblem.decrementSolutionCount();
        queryProblem.subtractPerceivedDifficulty(perceivedDifficulty);
    }

    @Transactional
    public void updateQueryProblemInfo(final Long sectorId, final String sectorName,
        final LocalDate settingDate, final boolean isExpired) {
        queryProblemRepository.updateQueryProblemInfo(sectorId, sectorName, settingDate, isExpired);
    }

    @Transactional
    public void addPerceivedDifficulty(final UUID problemId, final Integer perceivedDifficulty) {
        QueryProblem queryProblem = problemReadService.findQueryProblemById(problemId);
        queryProblem.addPerceivedDifficulty(perceivedDifficulty);
    }

    @Transactional
    public ProblemDetailResponseDto setPerceivedDifficulty(final UUID problemId,
        final Integer perceivedDifficulty) {
        QueryProblem queryProblem = problemReadService.findQueryProblemById(problemId);
        queryProblem.setPerceivedDifficulty(perceivedDifficulty);
        return ProblemDetailResponseDto.of(queryProblem);
    }

    public void updateQueryProblemFixedInfo(final List<Long> sectorIds, final String sectorName) {
        queryProblemRepository.updateSectorNameBySectorIds(sectorIds, sectorName);
    }
}
