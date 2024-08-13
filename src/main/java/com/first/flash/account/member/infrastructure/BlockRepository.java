package com.first.flash.account.member.infrastructure;

import com.first.flash.account.member.domain.MemberBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<MemberBlock, Long> {

    MemberBlock save(final MemberBlock memberBlock);
}
