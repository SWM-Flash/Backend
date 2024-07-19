package com.first.flash.climbing.problem.domain;

import com.first.flash.climbing.problem.infrastructure.paging.Cursor;
import com.first.flash.climbing.problem.infrastructure.paging.SortBy;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QueryProblemRepository {

    QueryProblem save(final QueryProblem queryProblem);

    Optional<QueryProblem> findById(final UUID id);

    List<QueryProblem> findAll(final Cursor preCursor, final SortBy sortBy, final int size,
        final Long gymId, final List<String> difficulty, final List<String> sector,
        final Boolean hasSolution);

    void updateRemovalDateBySectorId(final Long sectorId, final LocalDate removalDate);

    void expireProblemsBySectorIds(final List<Long> expiredSectorsIds);

    void updateQueryProblemInfo(final Long sectorId, final String sectorName,
        final LocalDate settingDate);
}
