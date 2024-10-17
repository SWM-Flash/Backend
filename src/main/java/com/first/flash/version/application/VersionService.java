package com.first.flash.version.application;

import java.util.NoSuchElementException;
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
        return versionJpaRepository.findTopByOrderByIdDesc()
                                   .map(version -> new VersionResponseDto(version.getAndroid(), version.getIos()))
                                   .orElseThrow(() -> new NoSuchElementException("No version information available"));
    }
}