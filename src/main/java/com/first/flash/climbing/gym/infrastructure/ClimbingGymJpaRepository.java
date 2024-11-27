package com.first.flash.climbing.gym.infrastructure;

import com.first.flash.climbing.gym.domian.ClimbingGym;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClimbingGymJpaRepository extends JpaRepository<ClimbingGym, Long> {

    ClimbingGym save(final ClimbingGym gym);

    Optional<ClimbingGym> findById(final Long id);
}
