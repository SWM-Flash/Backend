package com.first.flash.climbing.problem.application;

import com.first.flash.climbing.problem.domain.QueryProblemRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemsService {

    private final QueryProblemRepository queryProblemRepository;

    @Transactional
    public void changeRemovalDate(final Long sectorId, final LocalDate removalDate) {
        queryProblemRepository.updateRemovalDateBySectorId(sectorId, removalDate);
    }
}
