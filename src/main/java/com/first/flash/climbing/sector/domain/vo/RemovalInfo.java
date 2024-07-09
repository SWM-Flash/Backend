package com.first.flash.climbing.sector.domain.vo;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class RemovalInfo {

    private static final int FAKE_SECTOR_LIFETIME_DAYS = 30;
ㅇ
    private LocalDate removalDate;
    private Boolean isFakeRemovalDate;
    private Boolean isExpired;

    protected RemovalInfo(final LocalDate removalDate, final Boolean isFakeRemovalDate,
        final Boolean isExpired) {
        this.removalDate = removalDate;
        this.isFakeRemovalDate = isFakeRemovalDate;
        this.isExpired = isExpired;
    }

    public static RemovalInfo createBySettingDate(final LocalDate settingDate) {
        return new RemovalInfo(settingDate.plusDays(FAKE_SECTOR_LIFETIME_DAYS),
            true, false);
    }

    public static RemovalInfo createDefault(final LocalDate removalDate) {
        return new RemovalInfo(removalDate, false, false);
    }
}
