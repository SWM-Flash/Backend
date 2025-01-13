package com.first.flash.climbing.achievement.domain;

import com.first.flash.global.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Achievement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long solveCount;
    private String difficultyName;
    private String gymName;
    private Long gymInfoId;
    private UUID memberId;

    protected Achievement(final Long solveCount, final String gymName,
        final String difficultyName, final Long gymInfoId, final UUID memberId) {
        this.solveCount = solveCount;
        this.gymName = gymName;
        this.difficultyName = difficultyName;
        this.gymInfoId = gymInfoId;
        this.memberId = memberId;
    }

    public static Achievement createDefault(final Long solveCount, final String gymName,
        final String difficultyName,
        final Long gymInfoId, final UUID memberId) {
        return new Achievement(solveCount, gymName, difficultyName, gymInfoId, memberId);
    }
}
