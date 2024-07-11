package com.first.flash.climbing.problem.application;

import com.first.flash.climbing.problem.application.dto.ProblemsResponse;
import com.first.flash.climbing.problem.domain.ProblemRepository;
import com.first.flash.climbing.problem.domain.QueryProblem;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemReadService {

    private final ProblemRepository problemRepository;

    public ProblemsResponse findAll(final UUID lastId, final String sort, final Integer size,
        final List<String> difficulty, final List<String> sector, final Boolean hasSolution) {

        List<QueryProblem> queryProblems = problemRepository.findAll(lastId, sort, size, difficulty,
            sector, hasSolution);
        return ProblemsResponse.of(queryProblems);
    }
}
