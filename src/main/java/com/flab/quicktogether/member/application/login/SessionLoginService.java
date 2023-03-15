package com.flab.quicktogether.member.application.login;

import com.flab.quicktogether.common.auth.config.session.SessionConst;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.exception.BadCredentialsException;
import com.flab.quicktogether.member.exception.MemberNotFoundException;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SessionLoginService implements LoginService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession httpSession;

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

        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, member.getId());

        return member.getId();
    }

    public void logout() {
        if (httpSession != null) {
            httpSession.invalidate();
        }
    }

}
