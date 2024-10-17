package com.first.flash.version.application;

import com.first.flash.version.domain.AppVersion;
import com.first.flash.version.infrastructure.VersionJpaRepository;
import com.first.flash.version.application.dto.VersionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VersionService {

    private final VersionJpaRepository versionJpaRepository;

    public VersionResponseDto getLatestVersions() {
        AppVersion version = versionJpaRepository.findAll().get(0);
        return new VersionResponseDto(version.getAndroid(), version.getIos());
    }
}