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

    private static final int INITIAL_COUNT = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer solveCount;
    private Long gymInfoId;
    private UUID memberId;

    protected Achievement(final Integer solveCount, final Long gymInfoId, final UUID memberId) {
        this.solveCount = solveCount;
        this.gymInfoId = gymInfoId;
        this.memberId = memberId;
    }

    public static Achievement createDefault(final Long gymInfoId, final UUID memberId) {
        return new Achievement(INITIAL_COUNT, gymInfoId, memberId);
    }

    public void addSolveCount() {
        solveCount++;
    }
}
