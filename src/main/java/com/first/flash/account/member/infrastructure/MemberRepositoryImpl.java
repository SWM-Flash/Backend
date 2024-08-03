package com.first.flash.account.member.infrastructure;

import com.first.flash.account.member.domain.Member;
import com.first.flash.account.member.domain.MemberRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository jpaRepository;

    @Override
    public Member save(final Member member) {
        return jpaRepository.save(member);
    }

    @Override
    public Optional<Member> findById(final UUID id) {
        return jpaRepository.findById(id);
    }
}
