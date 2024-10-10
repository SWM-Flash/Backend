package com.first.flash.climbing.solution.domain;

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
        throw new IllegalArgumentException("Unknown difficulty: " + label);
    }
}