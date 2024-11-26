package com.first.flash.climbing.sector.domain;

import com.first.flash.climbing.sector.domain.vo.RemovalInfo;
import com.first.flash.climbing.sector.domain.vo.SectorName;
import com.first.flash.climbing.sector.exception.exceptions.InvalidRemovalDateException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private SectorName sectorName;
    private LocalDate settingDate;
    private RemovalInfo removalInfo;
    private String selectedImageUrl;
    private Long gymId;
    private Long sectorInfoId;

    protected Sector(final SectorName sectorName, final LocalDate settingDate,
        final RemovalInfo removalInfo, final Long gymId, final String selectedImageUrl,
        final Long sectorInfoId) {
        this.sectorName = sectorName;
        this.settingDate = settingDate;
        this.removalInfo = removalInfo;
        this.selectedImageUrl = selectedImageUrl;
        this.gymId = gymId;
        this.sectorInfoId = sectorInfoId;
    }

    public static Sector of(final SectorName sectorName, final LocalDate settingDate,
        final LocalDate removalDate, final Long gymId,
        final String selectedImageUrl, final Long sectorInfoId) {
        if (hasNoRemovalDate(removalDate)) {
            return createExceptRemovalDate(sectorName.getName(), sectorName.getAdminName(),
                settingDate, gymId, selectedImageUrl, sectorInfoId);
        }

        return createDefault(sectorName.getName(), sectorName.getAdminName(), settingDate,
            removalDate, gymId, selectedImageUrl, sectorInfoId);
    }

    public static Sector createExceptRemovalDate(final String sectorName,
        final String adminSectorName, final LocalDate settingDate, final Long gymId,
        final String selectedImageUrl, final Long sectorInfoId) {
        return new Sector(SectorName.of(sectorName, adminSectorName), settingDate,
            RemovalInfo.createBySettingDate(settingDate), gymId, selectedImageUrl, sectorInfoId);
    }

    public static Sector createDefault(final String sectorName, final String adminSectorName,
        final LocalDate settingDate, final LocalDate removalDate, final Long gymId,
        final String selectedImageUrl, final Long sectorInfoId) {
        validateRemovalDate(settingDate, removalDate);
        return new Sector(SectorName.of(sectorName, adminSectorName), settingDate,
            RemovalInfo.createDefault(removalDate), gymId, selectedImageUrl, sectorInfoId);
    }

    public LocalDate getRemovalDate() {
        return removalInfo.getRemovalDate();
    }

    public void updateRemovalDate(final LocalDate removalDate) {
        validateRemovalDate(settingDate, removalDate);
        removalInfo = RemovalInfo.createByNewRemovalDate(removalDate);
    }

    public void updateSector(final String sectorName, final String adminSectorName,
        final LocalDate settingDate, final LocalDate removalDate, final String selectedImageUrl) {
        validateRemovalDate(settingDate, removalDate);
        this.sectorName = SectorName.of(sectorName, adminSectorName);
        this.settingDate = settingDate;
        this.removalInfo = RemovalInfo.createByNewRemovalDate(removalDate);
        this.selectedImageUrl = selectedImageUrl;
    }

    public boolean isExpired() {
        return removalInfo.getIsExpired();
    }

    private static void validateRemovalDate(final LocalDate settingDate,
        final LocalDate removalDate) {
        if (removalDate.isBefore(settingDate)) {
            throw new InvalidRemovalDateException();
        }
    }

    private static boolean hasNoRemovalDate(final LocalDate removalDate) {
        return Objects.isNull(removalDate);
    }
}
