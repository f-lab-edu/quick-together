package com.flab.quicktogether.member.infrastructure;

import com.flab.quicktogether.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
