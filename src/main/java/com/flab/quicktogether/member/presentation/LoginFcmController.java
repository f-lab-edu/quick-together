package com.flab.quicktogether.member.presentation;

import com.flab.quicktogether.common.auth.Login;
import com.flab.quicktogether.common.auth.NotRequiredLoginCheck;
import com.flab.quicktogether.member.application.login.SessionFcmLoginService;
import com.flab.quicktogether.member.presentation.dto.request.LoginFcmRequest;
import com.flab.quicktogether.member.presentation.dto.response.MemberIdResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginFcmController {

    private final SessionFcmLoginService loginService;

    @NotRequiredLoginCheck
    @RequestMapping(path = "/fcm/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody @Valid LoginFcmRequest loginFcmRequest) {

        Long loginId = loginService.login(loginFcmRequest.getMemberName(),loginFcmRequest.getPassword(), loginFcmRequest.getToken());

        return ResponseEntity.ok(new MemberIdResponse(loginId));
    }

    @RequestMapping(path = "/fcm/login", method = RequestMethod.GET)
    public ResponseEntity checkLogin(@Login Long memberId) {
        return ResponseEntity.ok(new MemberIdResponse(memberId));
    }


    @RequestMapping(path = "/fcm/logout", method = RequestMethod.GET)
    public ResponseEntity logout(@Login Long memberId) {

        loginService.logout();

        return ResponseEntity.ok(new MemberIdResponse(memberId));
    }





}
