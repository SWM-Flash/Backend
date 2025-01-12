package com.first.flash.climbing.gym.domian;

import java.util.Optional;

public interface ClimbingGymInfoRepository {

    ClimbingGymInfo save(final ClimbingGymInfo climbingGymInfo);

    Optional<ClimbingGymInfo> findById(final Long id);
}
