package com.first.flash.climbing.gym.domian.vo;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class Difficulty {

    private String name;
    private Integer level;

    public Difficulty(final String name, final Integer level) {
        this.name = name;
        this.level = level;
    }

    public boolean hasSameName(final String name) {
        return this.name.equals(name);
    }
}
