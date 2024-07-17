package com.first.flash.climbing.problem.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DifficultyInfo {

    private String difficultyName;
    private Integer level;

    public static DifficultyInfo of(final String difficultyName, final Integer level) {
        return new DifficultyInfo(difficultyName, level);
    }
}
