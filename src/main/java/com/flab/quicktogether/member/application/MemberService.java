package com.flab.quicktogether.member.application;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.common.NotAuthorizedException;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {


    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        Member proto = new Member("proto");
        memberRepository.save(proto);
    }

    public Long login() {
        Member member = memberRepository.findById(1L)
                .orElseThrow(NotAuthenticateException::new);
        return member.getId();
    }
}
