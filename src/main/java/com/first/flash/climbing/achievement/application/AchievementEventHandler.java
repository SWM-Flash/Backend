package com.first.flash.climbing.achievement.application;

import com.first.flash.climbing.solution.domain.SolutionSavedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AchievementEventHandler {

    private final AchievementCommandService commandService;

    @EventListener
    @Transactional
    public void updateSolveCount(final SolutionSavedEvent event) {
        commandService.updateSolveCount(event.getSolutionId());
    }
}
