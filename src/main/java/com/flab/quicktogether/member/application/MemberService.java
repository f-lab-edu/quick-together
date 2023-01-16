package com.flab.quicktogether.member.application;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.exception.DuplicateMemberNameException;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.member.presentation.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createMember(MemberRequest memberRequest){
        memberRepository.findByMemberName(memberRequest.getMemberName()).ifPresent(member -> {
                    throw new DuplicateMemberNameException();
                });

        memberRequest.setPassword(passwordEncoder.encode(memberRequest.getMemberName()));
        Member member = memberRequest.toEntity();
        memberRepository.save(member);
    }




}
