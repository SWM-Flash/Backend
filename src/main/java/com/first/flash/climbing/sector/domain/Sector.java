package com.first.flash.climbing.sector.domain;

import com.first.flash.climbing.sector.domain.vo.RemovalInfo;
import com.first.flash.climbing.sector.domain.vo.SectorName;
import com.first.flash.climbing.sector.exception.exceptions.InvalidRemovalDateException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private SectorName sectorName;
    private LocalDate settingDate;
    private RemovalInfo removalInfo;
    private Long gymId;

    protected Sector(final SectorName sectorName, final LocalDate settingDate,
        final RemovalInfo removalInfo, final Long gymId) {
        this.sectorName = sectorName;
        this.settingDate = settingDate;
        this.removalInfo = removalInfo;
        this.gymId = gymId;
    }

    public static Sector createExceptRemovalDate(final String sectorName,
        final String adminSectorName, final LocalDate settingDate, final Long gymId) {
        return new Sector(SectorName.of(sectorName, adminSectorName), settingDate,
            RemovalInfo.createBySettingDate(settingDate), gymId);
    }

    public static Sector createDefault(final String sectorName, final String adminSectorName,
        final LocalDate settingDate, final LocalDate removalDate, final Long gymId) {
        validateRemovalDate(settingDate, removalDate);
        return new Sector(SectorName.of(sectorName, adminSectorName), settingDate,
            RemovalInfo.createDefault(removalDate), gymId);
    }

    public LocalDate getRemovalDate() {
        return removalInfo.getRemovalDate();
    }

    public void updateRemovalDate(final LocalDate removalDate) {
        validateRemovalDate(settingDate, removalDate);
        removalInfo = RemovalInfo.createDefault(removalDate);
    }

    public void updateSector(final String sectorName, final String adminSectorName,
        final LocalDate settingDate, final LocalDate removalDate, final Long gymId) {
        validateRemovalDate(settingDate, removalDate);
        this.sectorName = SectorName.of(sectorName, adminSectorName);
        this.settingDate = settingDate;
        this.removalInfo = RemovalInfo.createDefault(removalDate);
        this.gymId = gymId;
    }

    private static void validateRemovalDate(final LocalDate settingDate,
        final LocalDate removalDate) {
        if (removalDate.isBefore(settingDate)) {
            throw new InvalidRemovalDateException();
        }
    }
}
