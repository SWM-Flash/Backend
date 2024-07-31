package com.first.flash.climbing.problem.application;

import com.first.flash.climbing.problem.domain.ProblemIdConfirmEvent;
import com.first.flash.climbing.sector.domain.SectorExpiredEvent;
import com.first.flash.climbing.sector.domain.SectorInfoUpdatedEvent;
import com.first.flash.climbing.sector.domain.SectorRemovalDateUpdatedEvent;
import com.first.flash.climbing.solution.domain.SolutionSavedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ProblemEventHandler {

    private final ProblemsService problemsService;
    private final ProblemReadService problemReadService;

    @EventListener
    @Transactional
    public void changeRemovalDate(final SectorRemovalDateUpdatedEvent event) {
        problemsService.changeRemovalDate(event.getSectorId(), event.getRemovalDate());
    }

    @EventListener
    @Transactional
    public void expireProblem(final SectorExpiredEvent event) {
        problemsService.expireProblems(event.getExpiredSectorsIds());
    }

    @EventListener
    @Transactional
    public void updateProblemSolutionInfo(final SolutionSavedEvent event) {
        problemsService.updateProblemSolutionInfo(event.getProblemId());
    }

    @EventListener
    @Transactional
    public void updateQueryProblemInfo(final SectorInfoUpdatedEvent event) {
        problemsService.updateQueryProblemInfo(event.getId(), event.getSectorName(),
            event.getSettingDate());
    }

    @EventListener
    @Transactional
    public void confirmProblemId(final ProblemIdConfirmEvent event) {
        problemReadService.findProblemById(event.getProblemId());
    }
}
