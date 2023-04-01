package com.flab.quicktogether.member.application.login;

import com.flab.quicktogether.alarm.service.RedisAlarmTokenService;
import com.flab.quicktogether.common.auth.config.session.SessionConst;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.exception.BadCredentialsException;
import com.flab.quicktogether.member.exception.MemberNotFoundException;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SessionFcmLoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession httpSession;
    private final RedisAlarmTokenService redisAlarmTokenService;


    public Long login(String memberName, String password, String token) {
        Member member = memberRepository.findByMemberName(memberName)
                .orElseThrow(MemberNotFoundException::new);

        if (!passwordEncoder.matches(password,member.getPassword())) {
            throw new BadCredentialsException();
        }

        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, member.getId());

        redisAlarmTokenService.saveToken(member.getId(), token);

        return member.getId();
    }

    public void logout() {
        if (httpSession != null) {
            httpSession.invalidate();
        }
    }

}
