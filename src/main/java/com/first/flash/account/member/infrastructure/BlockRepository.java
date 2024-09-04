package com.first.flash.account.member.infrastructure;

import com.first.flash.account.member.domain.MemberBlock;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<MemberBlock, Long> {

    MemberBlock save(final MemberBlock memberBlock);

    @Query("SELECT mb.blocked.id FROM MemberBlock mb WHERE mb.blocker.id = :blockerId")
    List<UUID> findBlockedMembers(@Param("blockerId") final UUID blockerId);
}
