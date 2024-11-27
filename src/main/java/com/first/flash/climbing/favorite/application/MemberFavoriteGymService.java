package com.first.flash.climbing.favorite.application;

import com.first.flash.climbing.favorite.application.dto.MemberFavoriteGymResponseDto;
import com.first.flash.climbing.favorite.domain.MemberFavoriteGym;
import com.first.flash.climbing.favorite.domain.MemberFavoriteGymRepository;
import com.first.flash.global.util.AuthUtil;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberFavoriteGymService {

    private final MemberFavoriteGymRepository memberFavoriteGymRepository;

    public MemberFavoriteGymResponseDto saveMemberFavoriteGym(final Long gymId) {
        UUID memberId = AuthUtil.getId();
        MemberFavoriteGym memberFavoriteGym = MemberFavoriteGym.createDefault(memberId, gymId);
        MemberFavoriteGym savedMemberFavoriteGym = memberFavoriteGymRepository.save(
            memberFavoriteGym);
        return MemberFavoriteGymResponseDto.toDto(savedMemberFavoriteGym);
    }

    public List<Long> findFavoriteGymIdsByMemberId(final UUID memberId) {
        return memberFavoriteGymRepository.findByMemberId(memberId).stream()
                                          .map(MemberFavoriteGym::getGymId)
                                          .toList();
    }
}
