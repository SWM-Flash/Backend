package com.first.flash.climbing.achievement.application;

import com.first.flash.climbing.achievement.application.dto.AchievementCreateRequestDto;
import com.first.flash.climbing.achievement.application.dto.AchievementResponseDto;
import com.first.flash.climbing.achievement.domain.Achievement;
import com.first.flash.climbing.achievement.domain.AchievementRepository;
import com.first.flash.climbing.achievement.exception.exceptions.AchievementAccessDeniedException;
import com.first.flash.climbing.achievement.exception.exceptions.AchievementLimitExceededException;
import com.first.flash.climbing.gym.application.ClimbingGymInfoService;
import com.first.flash.climbing.gym.domian.ClimbingGymInfo;
import com.first.flash.climbing.solution.domain.SolutionMetaDataFetcher;
import com.first.flash.climbing.solution.infrastructure.dto.SolutionMetaData;
import com.first.flash.global.util.AuthUtil;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AchievementCommandService {

    private static final int LIMIT_SIZE = 2;

    private final AchievementQueryService queryService;
    private final AchievementRepository achievementRepository;
    private final ClimbingGymInfoService gymInfoService;
    private final SolutionMetaDataFetcher solutionMetaDataFetcher;

    @Transactional
    public AchievementResponseDto save(final AchievementCreateRequestDto requestDto) {
        UUID memberId = AuthUtil.getId();
        List<Achievement> achievements = achievementRepository.findByMemberId(memberId);

        if (achievements.size() == LIMIT_SIZE) {
            throw new AchievementLimitExceededException();
        }

        ClimbingGymInfo gymInfo = gymInfoService.findById(requestDto.gymInfoId());
        long solutionCount = solutionMetaDataFetcher.getSolutionCountByGymInfoDifficultyName(
            requestDto.gymInfoId(),
            requestDto.difficultyName(), memberId);

        Achievement newAchievement = Achievement.createDefault(solutionCount,
            gymInfo.getGymInfoName(),
            requestDto.difficultyName(), gymInfo.getId(), memberId);
        achievementRepository.save(newAchievement);
        return AchievementResponseDto.toDto(newAchievement);
    }

    @Transactional
    public void updateSolveCount(final Long solutionId) {
        SolutionMetaData solutionMetaData = solutionMetaDataFetcher.getSolutionMetaData(solutionId);
        UUID memberId = AuthUtil.getId();

        Optional<Achievement> targetAchievement = achievementRepository.findByGymInfoIdDifficultyName(
            solutionMetaData.gymInfoId(),
            solutionMetaData.difficultyName(), memberId);
        if (targetAchievement.isEmpty()) {
            return;
        }

        Achievement achievement = targetAchievement.get();
        long solutionCount = solutionMetaDataFetcher.getSolutionCountByGymInfoDifficultyName(
            solutionMetaData.gymInfoId(),
            solutionMetaData.difficultyName(), memberId);

        achievement.updateSolutionCount(solutionCount);
    }

    @Transactional
    public void deleteAchievement(final Long id) {
        Achievement achievement = queryService.findById(id);
        if (!isValidMember(achievement.getMemberId())) {
            throw new AchievementAccessDeniedException();
        }
        achievementRepository.deleteById(id);
    }

    private boolean isValidMember(final UUID memberId) {
        return AuthUtil.isSameId(memberId);
    }
}
