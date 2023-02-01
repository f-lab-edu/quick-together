package com.flab.quicktogether.member.application.login;

import com.flab.quicktogether.common.auth.config.jwt.JwtProvider;
import com.flab.quicktogether.common.auth.config.jwt.JwtTokenType;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.exception.BadCredentialsException;
import com.flab.quicktogether.member.exception.MemberNotFoundException;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
//@Service
public class JwtLoginService implements LoginService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletResponse response;
    private final JwtProvider jwtProvider;

    @PostConstruct
    public void init() {
        String password = passwordEncoder.encode("1111");
        Member proto = new Member("proto",password);
        Member hong = new Member("hong",password);

        memberRepository.save(proto);
        memberRepository.save(hong);
    }

    public Long login(String memberName, String password) {
        Member member = memberRepository.findByMemberName(memberName)
                .orElseThrow(MemberNotFoundException::new);

        if (!passwordEncoder.matches(password,member.getPassword())) {
            throw new BadCredentialsException();
        }

        String token = jwtProvider.createToken(memberName, member.getId());
        response.addHeader(HttpHeaders.AUTHORIZATION, String.format("%s %s",JwtTokenType.BEARER, token));


        return member.getId();
    }

    public void logout() {

    }
}
