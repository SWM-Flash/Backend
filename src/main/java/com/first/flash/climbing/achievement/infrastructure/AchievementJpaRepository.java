package com.first.flash.climbing.achievement.infrastructure;

import com.first.flash.climbing.achievement.domain.Achievement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementJpaRepository extends JpaRepository<Achievement, Long> {

    Achievement save(final Achievement achievement);

    Optional<Achievement> findById(final Long id);

    List<Achievement> findByMemberId(final UUID memberId);
}
