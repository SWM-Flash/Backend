package com.first.flash.climbing.problem.application;

import com.first.flash.climbing.hold.application.HoldService;
import com.first.flash.climbing.hold.domain.Hold;
import com.first.flash.climbing.problem.application.dto.ProblemDetailResponseDto;
import com.first.flash.climbing.problem.domain.Problem;
import com.first.flash.climbing.problem.domain.QueryProblem;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemsHoldService {

    private final ProblemReadService problemReadService;
    private final HoldService holdService;

    @Transactional
    public ProblemDetailResponseDto updateHold(final UUID problemId,
        final Long holdId) {
        Hold hold = holdService.findById(holdId);

        Problem problem = problemReadService.findProblemById(problemId);
        QueryProblem queryProblem = problemReadService.findQueryProblemById(problemId);

        problem.setHoldInfo(hold.getId());
        queryProblem.setHoldInfo(hold.getId(), hold.getColorName(), hold.getColorCode());

        return ProblemDetailResponseDto.of(queryProblem);
    }

}
