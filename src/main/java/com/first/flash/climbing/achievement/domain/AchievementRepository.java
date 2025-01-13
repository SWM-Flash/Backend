package com.first.flash.climbing.achievement.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AchievementRepository {

    Achievement save(final Achievement achievement);

    Optional<Achievement> findById(final Long id);

    List<Achievement> findByMemberId(final UUID memberId);
}
