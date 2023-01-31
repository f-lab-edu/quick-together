package com.flab.quicktogether.member.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class BadCredentialsException extends ApplicationException {
    public BadCredentialsException() {
        super("BadCredentialsException", HttpStatus.UNAUTHORIZED);
    }
}
