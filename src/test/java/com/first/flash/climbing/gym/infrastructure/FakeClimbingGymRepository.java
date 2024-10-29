package com.first.flash.climbing.gym.infrastructure;

import com.first.flash.climbing.gym.domian.ClimbingGym;
import com.first.flash.climbing.gym.domian.ClimbingGymRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeClimbingGymRepository implements ClimbingGymRepository {

    final private Map<Long, ClimbingGym> db = new HashMap<>();
    private Long id = 0L;

    @Override
    public ClimbingGym save(final ClimbingGym gym) {
        ClimbingGym savedGym = new ClimbingGym(id++, gym.getGymName(), gym.getThumbnailUrl(),
            gym.getMapImageUrl(), "example image", gym.getDifficulties());

        db.put(savedGym.getId(), savedGym);
        return savedGym;
    }

    @Override
    public Optional<ClimbingGym> findById(final Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public List<ClimbingGym> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public List<String> findGymSectorNamesById(final Long id) {
        return null;
    }
}
