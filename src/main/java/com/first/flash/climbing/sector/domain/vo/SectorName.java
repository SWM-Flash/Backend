package com.first.flash.climbing.sector.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class SectorName {
    private String name;
    private String adminName;

    protected SectorName(final String name, final String adminName) {
        this.name = name;
        this.adminName = adminName;
    }

    public static SectorName of(final String name, final String adminName) {
        return new SectorName(name, adminName);
    }
}
