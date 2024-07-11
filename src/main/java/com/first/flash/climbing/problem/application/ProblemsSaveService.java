package com.first.flash.climbing.problem.application;

import com.first.flash.climbing.gym.application.ClimbingGymService;
import com.first.flash.climbing.gym.domian.ClimbingGym;
import com.first.flash.climbing.problem.application.dto.ProblemCreateResponseDto;
import com.first.flash.climbing.problem.domain.Problem;
import com.first.flash.climbing.problem.domain.ProblemRepository;
import com.first.flash.climbing.problem.domain.QueryProblem;
import com.first.flash.climbing.problem.domain.ProblemsCreateService;
import com.first.flash.climbing.problem.domain.QueryProblemRepository;
import com.first.flash.climbing.problem.domain.dto.ProblemCreateRequestDto;
import com.first.flash.climbing.sector.application.SectorService;
import com.first.flash.climbing.sector.domain.Sector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemsSaveService {

    private final ProblemRepository problemRepository;
    private final QueryProblemRepository queryProblemRepository;
    private final ClimbingGymService climbingGymService;
    private final SectorService sectorService;
    private final ProblemsCreateService problemsCreateService;

    @Transactional
    public ProblemCreateResponseDto saveProblems(final Long gymId, final Long sectorId,
        final ProblemCreateRequestDto createRequestDto) {
        ClimbingGym climbingGym = climbingGymService.findClimbingGymById(gymId);
        Sector sector = sectorService.findById(sectorId);
        Problem problem = problemsCreateService.createProblem(climbingGym, sector,
            createRequestDto);
        QueryProblem queryProblem = problemsCreateService.createQueryProblem(climbingGym,
            sector, problem);
        problemRepository.save(problem);
        queryProblemRepository.save(queryProblem);
        return ProblemCreateResponseDto.toDto(problem);
    }
}
