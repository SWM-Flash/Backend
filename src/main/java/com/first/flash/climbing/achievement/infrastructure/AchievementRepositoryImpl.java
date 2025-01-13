package com.first.flash.climbing.achievement.infrastructure;

import com.first.flash.climbing.achievement.domain.Achievement;
import com.first.flash.climbing.achievement.domain.AchievementRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AchievementRepositoryImpl implements AchievementRepository {

    private final AchievementJpaRepository jpaRepository;
    private final AchievementQueryDslRepository queryDslRepository;

    @Override
    public Achievement save(final Achievement achievement) {
        return jpaRepository.save(achievement);
    }

    @Override
    public Optional<Achievement> findById(final Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Achievement> findByMemberId(final UUID memberId) {
        return jpaRepository.findByMemberId(memberId);
    }

    @Override
    public long findSolutionCountByGymInfoIdDifficulty(final Long gymInfoId,
        final String difficultyName, final UUID memberId) {
        return queryDslRepository.findSolutionCountByGymNameDifficulty(gymInfoId, difficultyName,
            memberId);
    }

    @Override
    public void deleteById(final Long id) {
        jpaRepository.deleteById(id);
    }
}
