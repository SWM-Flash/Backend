package com.first.flash.climbing.gym.domian.vo;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class Difficulty {

    @NotEmpty(message = "난이도 이름은 필수입니다.")
    private String name;
    @NotNull(message = "난이도 레벨은 필수입니다.")
    private Integer level;

    public Difficulty(final String name, final Integer level) {
        this.name = name;
        this.level = level;
    }

    public boolean hasSameName(final String name) {
        return this.name.equals(name);
    }
}
