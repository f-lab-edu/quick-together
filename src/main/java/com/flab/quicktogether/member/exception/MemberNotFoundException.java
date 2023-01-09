package com.flab.quicktogether.member.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends ApplicationException {
    public MemberNotFoundException() {
        super("MemberNotFoundException", HttpStatus.NOT_FOUND);
    }
}
