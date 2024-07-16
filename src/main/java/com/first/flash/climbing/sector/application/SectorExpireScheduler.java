package com.first.flash.climbing.sector.application;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SectorExpireScheduler {

    private final SectorService sectorService;

    @Scheduled(cron = "0 0 0 * * *")
    public void expireSector() {
        sectorService.expireSector();
    }
}
