package com.first.flash.climbing.hold.domain;

import com.first.flash.climbing.sector.domain.Sector;
import java.util.List;
import java.util.Optional;

public interface HoldRepository {

    Hold save(final Hold hold);

    Optional<Hold> findById(final Long id);

    List<Hold> findAll();
}
