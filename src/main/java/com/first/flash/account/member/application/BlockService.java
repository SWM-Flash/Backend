package com.first.flash.account.member.application;

import com.first.flash.account.member.application.dto.MemberBlockResponse;
import com.first.flash.account.member.domain.Member;
import com.first.flash.account.member.domain.MemberBlock;
import com.first.flash.account.member.infrastructure.BlockRepository;
import com.first.flash.global.util.AuthUtil;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlockService {

    private final BlockRepository blockRepository;
    private final MemberService memberService;

    @Transactional
    public MemberBlockResponse blockMember(final UUID blockedUser) {
        UUID blockerId = AuthUtil.getId();
        Member blocker = memberService.findById(blockerId);
        Member blocked = memberService.findById(blockedUser);
        MemberBlock.blockMember(blocker, blocked);
        MemberBlock blockResult = blockRepository.save(MemberBlock.blockMember(blocker, blocked));
        blockRepository.save(blockResult);
        return MemberBlockResponse.toDto(blocked);
    }
}
