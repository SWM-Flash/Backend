package com.first.flash.climbing.problem.infrastructure;

import com.first.flash.climbing.problem.domain.QueryProblem;
import com.first.flash.climbing.problem.domain.QueryProblemRepository;
import com.first.flash.climbing.problem.infrastructure.paging.ProblemCursor;
import com.first.flash.climbing.problem.infrastructure.paging.ProblemSortBy;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QueryProblemRepositoryImpl implements QueryProblemRepository {

    private final QueryProblemJpaRepository jpaRepository;
    private final QueryProblemQueryDslRepository queryProblemQueryDslRepository;

    @Override
    public QueryProblem save(final QueryProblem queryProblem) {
        return jpaRepository.save(queryProblem);
    }

    @Override
    public Optional<QueryProblem> findById(final UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<QueryProblem> findAll(final ProblemCursor prevProblemCursor,
        final ProblemSortBy problemSortBy, final int size,
        final Long gymId, final List<String> difficulty, final List<String> sector,
        final Boolean hasSolution, final Boolean isHoney) {
        return queryProblemQueryDslRepository.findAll(prevProblemCursor, problemSortBy, size,
            gymId, difficulty, sector, hasSolution, isHoney);
    }

    @Override
    public void updateRemovalDateBySectorId(final Long sectorId, final LocalDate removalDate, final boolean isExpired) {
        queryProblemQueryDslRepository.updateRemovalDateBySectorId(sectorId, removalDate, isExpired);
    }

    @Override
    public void expireProblemsBySectorIds(final List<Long> expiredSectorsIds) {
        queryProblemQueryDslRepository.expireProblemsBySectorIds(expiredSectorsIds);
    }

    @Override
    public void updateQueryProblemInfo(final Long sectorId, final String sectorName,
        final LocalDate settingDate, final boolean isExpired) {
        queryProblemQueryDslRepository.updateQueryProblemInfo(sectorId, sectorName, settingDate,
            isExpired);
    }

    @Override
    public void updateSectorNameBySectorIds(final List<Long> sectorIds, final String sectorName) {
        queryProblemQueryDslRepository.updateSectorNameBySectorIds(sectorIds, sectorName);
    }

    @Override
    public void deleteByProblemId(UUID problemId) {
        jpaRepository.deleteById(problemId);
    }
}
