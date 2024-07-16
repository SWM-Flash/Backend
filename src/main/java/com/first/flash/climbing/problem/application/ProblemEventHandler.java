package com.first.flash.climbing.problem.application;

import com.first.flash.climbing.sector.domain.SectorRemovalDateUpdatedEvent;
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
}
