package com.first.flash.climbing.hold.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Hold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String colorName;
    private String colorCode;

    protected Hold(final String colorName, final String colorCode) {
        this.colorName = colorName;
        this.colorCode = colorCode;
    }

    public static Hold of(final String colorName, final String colorCode) {
        return new Hold(colorName, colorCode);
    }
}
