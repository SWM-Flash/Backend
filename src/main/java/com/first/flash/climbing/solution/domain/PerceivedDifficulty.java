package com.first.flash.climbing.solution.domain;

import com.first.flash.climbing.solution.exception.exceptions.PerceivedDifficultyNotFoundException;
import lombok.AllArgsConstructor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PerceivedDifficulty {
    EASY(-1, "쉬움"),
    NORMAL(0, "보통"),
    HARD(1, "어려움");

    private final Integer value;
    private final String label;

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static PerceivedDifficulty fromString(final String label) {
        for (PerceivedDifficulty difficulty : values()) {
            if (difficulty.label.equals(label)) {
                return difficulty;
            }
        }
        throw new PerceivedDifficultyNotFoundException(label);
    }

    public static PerceivedDifficulty fromValue(int value) {
        for (PerceivedDifficulty difficulty : values()) {
            if (difficulty.value == value) {
                return difficulty;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

    public int calculateDifferenceFrom(PerceivedDifficulty other) {
        return this.value - other.value;
    }
}