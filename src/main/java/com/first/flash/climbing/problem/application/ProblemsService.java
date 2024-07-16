package com.first.flash.climbing.problem.application;

import com.first.flash.climbing.problem.domain.ProblemRepository;
import com.first.flash.climbing.problem.domain.QueryProblemRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemsService {

    private final QueryProblemRepository queryProblemRepository;
    private final ProblemRepository problemRepository;

    @Transactional
    public void changeRemovalDate(final Long sectorId, final LocalDate removalDate) {
        queryProblemRepository.updateRemovalDateBySectorId(sectorId, removalDate);
    }

    @Transactional
    public void expireProblems(final List<Long> expiredSectorsIds) {
        queryProblemRepository.expireSectorsById(expiredSectorsIds);
        problemRepository.expireSectorsById(expiredSectorsIds);
    }
}