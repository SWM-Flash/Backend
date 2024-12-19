package com.first.flash.climbing.hold.infrastructure;

import com.first.flash.climbing.hold.domain.Hold;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoldJpaRepository extends JpaRepository<Hold, Long> {

    Hold save(final Hold hold);

    Optional<Hold> findById(final Long id);

    List<Hold> findAll();
}
