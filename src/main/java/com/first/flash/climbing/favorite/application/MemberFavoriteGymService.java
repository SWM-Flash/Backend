package com.first.flash.climbing.favorite.application;

import com.first.flash.climbing.favorite.application.dto.MemberFavoriteGymResponseDto;
import com.first.flash.climbing.favorite.domain.MemberFavoriteGym;
import com.first.flash.climbing.favorite.domain.MemberFavoriteGymRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberFavoriteGymService {

    private final MemberFavoriteGymRepository memberFavoriteGymRepository;

    public MemberFavoriteGymResponseDto saveMemberFavoriteGym(final UUID memberId, final Long gymId) {
        MemberFavoriteGym memberFavoriteGym = MemberFavoriteGym.createDefault(memberId, gymId);
        MemberFavoriteGym savedMemberFavoriteGym = memberFavoriteGymRepository.save(memberFavoriteGym);
        return MemberFavoriteGymResponseDto.toDto(savedMemberFavoriteGym);
    }
}
