package com.first.flash.account.member.infrastructure;

import com.first.flash.account.member.domain.Member;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, UUID> {

}
