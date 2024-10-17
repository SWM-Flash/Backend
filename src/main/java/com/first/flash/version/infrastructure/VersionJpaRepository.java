package com.first.flash.version.infrastructure;

import com.first.flash.version.domain.AppVersion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionJpaRepository extends JpaRepository<AppVersion, Long> {

    List<AppVersion> findAll();
}