package com.first.flash.climbing.favorite.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberFavoriteGymRepository {

    MemberFavoriteGym save(final MemberFavoriteGym memberFavoriteGym);

    Optional<MemberFavoriteGym> findById(final Long id);

    List<MemberFavoriteGym> findByMemberId(final UUID memberId);

    Optional<MemberFavoriteGym> findByMemberIdAndGymId(final UUID memberId, final Long gymId);

    void delete(final MemberFavoriteGym memberFavoriteGym);
}
