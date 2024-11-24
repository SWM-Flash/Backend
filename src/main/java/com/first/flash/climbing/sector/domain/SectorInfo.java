package com.first.flash.climbing.sector.domain;

import com.first.flash.climbing.sector.domain.vo.SectorName;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class SectorInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String selectedImageUrl;
    private SectorName sectorName;
    private Long gymId;

    protected SectorInfo(final String selectedImageUrl, final SectorName sectorName,
        final Long gymId) {
        this.selectedImageUrl = selectedImageUrl;
        this.sectorName = sectorName;
        this.gymId = gymId;
    }

    public static SectorInfo createDefault(final String sectorName, final String adminSectorName,
        final Long gymId, final String selectedImageUrl) {
        return new SectorInfo(selectedImageUrl, SectorName.of(sectorName, adminSectorName), gymId);
    }

    public void updateSectorInfo(final String sectorName, final String adminSectorName,
        final String selectedImageUrl) {
        this.sectorName = SectorName.of(sectorName, adminSectorName);
        this.selectedImageUrl = selectedImageUrl;
    }
}
