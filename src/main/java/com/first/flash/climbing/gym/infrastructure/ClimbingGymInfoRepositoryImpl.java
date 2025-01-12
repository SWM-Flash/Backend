package com.first.flash.climbing.gym.infrastructure;

import com.first.flash.climbing.gym.domian.ClimbingGymInfo;
import com.first.flash.climbing.gym.domian.ClimbingGymInfoRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClimbingGymInfoRepositoryImpl implements ClimbingGymInfoRepository {

    private final ClimbingGymInfoJpaRepository jpaRepository;

    @Override
    public ClimbingGymInfo save(final ClimbingGymInfo climbingGymInfo) {
        return jpaRepository.save(climbingGymInfo);
    }

    @Override
    public Optional<ClimbingGymInfo> findById(final Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<ClimbingGymInfo> findAll() {
        return jpaRepository.findAll();
    }
}
