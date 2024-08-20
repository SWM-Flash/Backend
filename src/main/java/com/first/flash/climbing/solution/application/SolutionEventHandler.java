package com.first.flash.climbing.solution.application;

import com.first.flash.account.member.domain.MemberInfoUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SolutionEventHandler {

    private final SolutionSaveService solutionSaveService;

    @EventListener
    @Transactional
    public void updateSolutionInfo(final MemberInfoUpdatedEvent event) {
        solutionSaveService.updateMemberInfo(event.getMemberId(), event.getNickName(),
            event.getInstagramId());
    }
}
