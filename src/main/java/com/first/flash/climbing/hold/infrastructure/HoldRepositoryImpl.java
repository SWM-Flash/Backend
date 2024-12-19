package com.first.flash.climbing.hold.infrastructure;

import com.first.flash.climbing.hold.domain.Hold;
import com.first.flash.climbing.hold.domain.HoldRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HoldRepositoryImpl implements HoldRepository {

    private final HoldJpaRepository holdJpaRepository;

    @Override
    public Hold save(Hold hold) {
        return holdJpaRepository.save(hold);
    }

    @Override
    public Optional<Hold> findById(Long id) {
        return holdJpaRepository.findById(id);
    }

    @Override
    public List<Hold> findAll() {
        return holdJpaRepository.findAll();
    }
}
