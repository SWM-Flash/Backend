package com.first.flash.version.infrastructure;

import com.first.flash.version.domain.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VersionJpaRepository extends JpaRepository<AppVersion, Long> {

    Optional<AppVersion> findTopByOrderByIdDesc();
}