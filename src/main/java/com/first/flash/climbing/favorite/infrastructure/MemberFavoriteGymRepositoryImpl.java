package com.first.flash.climbing.favorite.infrastructure;

import com.first.flash.climbing.favorite.domain.MemberFavoriteGym;
import com.first.flash.climbing.favorite.domain.MemberFavoriteGymRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberFavoriteGymRepositoryImpl implements MemberFavoriteGymRepository {

    private final MemberFavoriteGymJpaRepository memberFavoriteGymJpaRepository;

    @Override
    public MemberFavoriteGym save(final MemberFavoriteGym memberFavoriteGym) {
        return memberFavoriteGymJpaRepository.save(memberFavoriteGym);
    }

    @Override
    public Optional<MemberFavoriteGym> findById(final Long id) {
        return memberFavoriteGymJpaRepository.findById(id);
    }

    @Override
    public List<MemberFavoriteGym> findByMemberId(final UUID memberId) {
        return memberFavoriteGymJpaRepository.findByMemberId(memberId);
    }

    @Override
    public Optional<MemberFavoriteGym> findByMemberIdAndGymId(final UUID memberId,
        final Long gymId) {
        return memberFavoriteGymJpaRepository.findByMemberIdAndGymId(memberId, gymId);
    }

    @Override
    public void delete(final MemberFavoriteGym memberFavoriteGym) {
        memberFavoriteGymJpaRepository.delete(memberFavoriteGym);
    }
}
