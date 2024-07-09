package com.first.flash.climbing.gym.infrastructure;

import com.first.flash.climbing.gym.domian.ClimbingGym;
import com.first.flash.climbing.gym.domian.ClimbingGymRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClimbingGymRepositoryImpl implements ClimbingGymRepository {

    private final ClimbingGymJpaRepository climbingGymJpaRepository;

    @Override
    public Long save(final ClimbingGym gym) {
        return climbingGymJpaRepository.save(gym).getId();
    }

    @Override
    public Optional<ClimbingGym> findById(final Long id) {
        return climbingGymJpaRepository.findById(id);
    }

    @Override
    public List<ClimbingGym> findAll() {
        return climbingGymJpaRepository.findAll();
    }
}
