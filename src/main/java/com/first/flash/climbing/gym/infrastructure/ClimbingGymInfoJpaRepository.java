package com.first.flash.climbing.gym.infrastructure;

import com.first.flash.climbing.gym.domian.ClimbingGymInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClimbingGymInfoJpaRepository extends JpaRepository<ClimbingGymInfo, Long> {

    ClimbingGymInfo save(final ClimbingGymInfo climbingGymInfo);

    Optional<ClimbingGymInfo> findById(final Long id);

    List<ClimbingGymInfo> findAll();
}
