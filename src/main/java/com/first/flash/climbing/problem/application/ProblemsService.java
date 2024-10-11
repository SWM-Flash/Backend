package com.first.flash.climbing.problem.application;

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
    public void changeRemovalDate(final Long sectorId, final LocalDate removalDate) {
        queryProblemRepository.updateRemovalDateBySectorId(sectorId, removalDate);
    }

    @Transactional
    public void expireProblems(final List<Long> expiredSectorsIds) {
        queryProblemRepository.expireProblemsBySectorIds(expiredSectorsIds);
        problemRepository.expireProblemsBySectorIds(expiredSectorsIds);
    }

    @Transactional
    public void updateProblemSolutionInfo(final UUID problemId) {
        QueryProblem queryProblem = problemReadService.findQueryProblemById(problemId);
        queryProblem.addSolutionCount();
    }

    @Transactional
    public void updateProblemDeletedSolutionInfo(final UUID problemId, final Integer perceivedDifficulty) {
        QueryProblem queryProblem = problemReadService.findQueryProblemById(problemId);
        queryProblem.decrementSolutionCount();
        queryProblemRepository.updatePerceivedDifficulty(problemId, perceivedDifficulty);
    }

    @Transactional
    public void updateQueryProblemInfo(final Long sectorId, final String sectorName,
        final LocalDate settingDate) {
        queryProblemRepository.updateQueryProblemInfo(sectorId, sectorName, settingDate);
    }

    @Transactional
    public void updatePerceivedDifficulty(final UUID problemId, final Integer perceivedDifficulty) {
        queryProblemRepository.updatePerceivedDifficulty(problemId, perceivedDifficulty);
    }
}
