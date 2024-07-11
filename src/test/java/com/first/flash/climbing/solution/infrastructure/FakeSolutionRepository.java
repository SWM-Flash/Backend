package com.first.flash.climbing.solution.infrastructure;

import com.first.flash.climbing.solution.domain.Solution;
import com.first.flash.climbing.solution.domain.SolutionRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeSolutionRepository implements SolutionRepository {

    private final static Long DEFAULT_OPTIONAL_WEIGHT = 0L;
    private final static Long DEFAULT_PROBLEM_ID = 1L;

    final private Map<Long, Solution> db = new HashMap<>();
    private Long id = 0L;


    @Override
    public Solution save(Solution solution) {
        Solution savedSolution = new Solution(id++, solution.getUploader(), solution.getReview(),
            solution.getInstagramId(), solution.getVideoUrl(), DEFAULT_OPTIONAL_WEIGHT,
            DEFAULT_PROBLEM_ID);

        db.put(savedSolution.getId(), savedSolution);
        return savedSolution;
    }

    @Override
    public Optional<Solution> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public List<Solution> findAll() {
        return new ArrayList<>(db.values());
    }
}
