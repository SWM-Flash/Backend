package com.first.flash.climbing.favorite.application;

import com.first.flash.climbing.favorite.application.dto.MemberFavoriteGymResponseDto;
import com.first.flash.climbing.favorite.domain.MemberFavoriteGym;
import com.first.flash.climbing.favorite.domain.MemberFavoriteGymRepository;
import com.first.flash.global.util.AuthUtil;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberFavoriteGymService {

    private final MemberFavoriteGymRepository memberFavoriteGymRepository;

    public MemberFavoriteGymResponseDto toggleMemberFavoriteGym(final Long gymId) {
        UUID memberId = AuthUtil.getId();
        Optional<MemberFavoriteGym> favoriteGym = memberFavoriteGymRepository.findByMemberIdAndGymId(memberId, gymId);

        if (favoriteGym.isPresent()) {
            memberFavoriteGymRepository.delete(favoriteGym.get());
        } else {
            MemberFavoriteGym memberFavoriteGym = MemberFavoriteGym.createDefault(memberId, gymId);
            memberFavoriteGymRepository.save(memberFavoriteGym);
        }
        return MemberFavoriteGymResponseDto.toDtoByStatus(favoriteGym.isPresent());
    }

    public List<Long> findFavoriteGymIdsByMemberId(final UUID memberId) {
        return memberFavoriteGymRepository.findByMemberId(memberId).stream()
                                          .map(MemberFavoriteGym::getGymId)
                                          .toList();
    }
}
