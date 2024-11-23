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
    private String selectedImageUrl;
    private SectorName sectorName;
    private LocalDate settingDate;
    private RemovalInfo removalInfo;
    private Long gymId;

    protected Sector(final SectorName sectorName, final LocalDate settingDate,
        final RemovalInfo removalInfo, final Long gymId, final String selectedImageUrl) {
        this.sectorName = sectorName;
        this.settingDate = settingDate;
        this.removalInfo = removalInfo;
        this.selectedImageUrl = selectedImageUrl;
        this.gymId = gymId;
    }

    public static Sector createExceptRemovalDate(final String sectorName,
        final String adminSectorName, final LocalDate settingDate, final Long gymId,
        final String selectedImageUrl) {
        return new Sector(SectorName.of(sectorName, adminSectorName), settingDate,
            RemovalInfo.createBySettingDate(settingDate), gymId, selectedImageUrl);
    }

    public static Sector createDefault(final String sectorName, final String adminSectorName,
        final LocalDate settingDate, final LocalDate removalDate, final Long gymId,
        final String selectedImageUrl) {
        validateRemovalDate(settingDate, removalDate);
        return new Sector(SectorName.of(sectorName, adminSectorName), settingDate,
            RemovalInfo.createDefault(removalDate), gymId, selectedImageUrl);
    }

    public LocalDate getRemovalDate() {
        return removalInfo.getRemovalDate();
    }

    public void updateRemovalDate(final LocalDate removalDate) {
        validateRemovalDate(settingDate, removalDate);
        removalInfo = RemovalInfo.createDefault(removalDate);
    }

    public void updateSector(final String sectorName, final String adminSectorName,
        final LocalDate settingDate, final LocalDate removalDate, final Long gymId,
        final String selectedImageUrl) {
        validateRemovalDate(settingDate, removalDate);
        this.sectorName = SectorName.of(sectorName, adminSectorName);
        this.settingDate = settingDate;
        this.removalInfo = RemovalInfo.createDefault(removalDate);
        this.selectedImageUrl = selectedImageUrl;
        this.gymId = gymId;
    }

    private static void validateRemovalDate(final LocalDate settingDate,
        final LocalDate removalDate) {
        if (removalDate.isBefore(settingDate)) {
            throw new InvalidRemovalDateException();
        }
    }
}
