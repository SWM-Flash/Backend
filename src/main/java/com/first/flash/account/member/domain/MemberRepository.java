package com.first.flash.account.member.domain;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {

    Member save(final Member member);
    Optional<Member> findById(final UUID id);
    Optional<Member> findByEmail(final String email);
    boolean existsByNickName(final String nickName);
}
