package com.first.flash.climbing.problem.application;

import com.first.flash.climbing.sector.domain.SectorExpiredEvent;
import com.first.flash.climbing.sector.domain.SectorRemovalDateUpdatedEvent;
import com.first.flash.climbing.solution.domain.SolutionSavedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ProblemEventHandler {

    private final ProblemsService problemsService;

    @TransactionalEventListener(
        classes = SectorRemovalDateUpdatedEvent.class,
        phase = TransactionPhase.AFTER_COMMIT
    )
    @Async
    public void changeRemovalDate(final SectorRemovalDateUpdatedEvent event) {
        problemsService.changeRemovalDate(event.getSectorId(), event.getRemovalDate());
    }

    @TransactionalEventListener(
        classes = SectorExpiredEvent.class,
        phase = TransactionPhase.AFTER_COMMIT
    )
    @Async
    public void expireProblem(final SectorExpiredEvent event) {
        problemsService.expireProblems(event.getExpiredSectorsIds());
    }

    @TransactionalEventListener(
        classes = SolutionSavedEvent.class,
        phase = TransactionPhase.AFTER_COMMIT
    )
    @Async
    public void updateProblemSolutionInfo(final SolutionSavedEvent event) {
        problemsService.updateProblemSolutionInfo(event.getProblemId());
    }
}
