package com.first.flash.climbing.solution.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PerceivedDifficultyConverter implements AttributeConverter<PerceivedDifficulty, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PerceivedDifficulty attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public PerceivedDifficulty convertToEntityAttribute(Integer dbData) {
        return dbData != null ? PerceivedDifficulty.fromValue(dbData) : null;
    }
}
