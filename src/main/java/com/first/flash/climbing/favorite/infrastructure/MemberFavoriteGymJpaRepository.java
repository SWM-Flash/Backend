package com.first.flash.climbing.favorite.infrastructure;

import com.first.flash.climbing.favorite.domain.MemberFavoriteGym;
import com.first.flash.climbing.favorite.domain.MemberFavoriteGymRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberFavoriteGymJpaRepository extends JpaRepository<MemberFavoriteGym, Long> {

    MemberFavoriteGym save(final MemberFavoriteGym memberFavoriteGym);

    Optional<MemberFavoriteGym> findById(final Long id);

    List<MemberFavoriteGym> findByMemberId(final UUID memberId);
}
