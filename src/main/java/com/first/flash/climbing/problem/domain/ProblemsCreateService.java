package com.first.flash.climbing.problem.domain;

import com.first.flash.climbing.gym.domian.ClimbingGym;
import com.first.flash.climbing.gym.domian.vo.Difficulty;
import com.first.flash.climbing.problem.domain.dto.ProblemCreateRequestDto;
import com.first.flash.climbing.problem.util.UUIDGenerator;
import com.first.flash.climbing.sector.domain.Sector;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemsCreateService {

    private static final Boolean DEFAULT_HAS_SOLUTION = false;
    private static final Integer INITIAL_SOLUTION_COUNT = 0;
    private static final Long INITIAL_RECOMMENDATION_VALUE = 0L;

    private final UUIDGenerator uuidGenerator;

    public Problem createProblem(final ClimbingGym climbingGym, final Sector sector,
        final ProblemCreateRequestDto createRequestDto) {
        Difficulty difficulty = climbingGym.getDifficultyByName(createRequestDto.difficulty());
        UUID generatedUUID = uuidGenerator.generate();

        return Problem.createDefault(generatedUUID, createRequestDto.imageUrl(),
            difficulty.getName(), difficulty.getLevel(), climbingGym.getId(), sector.getId(), createRequestDto.source());
    }

    public QueryProblem createQueryProblem(final ClimbingGym climbingGym, final Sector sector,
        final Problem problem) {
        return QueryProblem.builder()
                           .id(problem.getId())
                           .imageUrl(problem.getImageUrl())
                           .views(problem.getViews())
                           .isExpired(problem.getIsExpired())
                           .hasSolution(DEFAULT_HAS_SOLUTION)
                           .recommendationValue(INITIAL_RECOMMENDATION_VALUE)
                           .solutionCount(INITIAL_SOLUTION_COUNT)
                           .isFakeRemovalDate(sector.getRemovalInfo().getIsFakeRemovalDate())
                           .difficultyName(problem.getDifficultyInfo().getDifficultyName())
                           .difficultyLevel(problem.getDifficultyInfo().getLevel())
                           .optionalWeight(problem.getOptionalWeight())
                           .source(problem.getSource())
                           .gymId(problem.getGymId())
                           .gymName(climbingGym.getGymName())
                           .sectorId(problem.getSectorId())
                           .sectorName(sector.getSectorName().getName())
                           .settingDate(sector.getSettingDate())
                           .removalDate(sector.getRemovalDate())
                           .build();
    }
}
