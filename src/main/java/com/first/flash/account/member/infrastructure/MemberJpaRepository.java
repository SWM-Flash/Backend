package com.first.flash.account.member.infrastructure;

import com.first.flash.account.member.domain.Member;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, UUID> {

    Member save(final Member member);

    Optional<Member> findById(final UUID id);

    Optional<Member> findMemberBySocialId(final String email);

    boolean existsByNickName(final String nickName);

    void deleteById(final UUID id);
}
