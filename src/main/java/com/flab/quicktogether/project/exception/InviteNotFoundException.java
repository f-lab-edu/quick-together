package com.flab.quicktogether.project.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class InviteNotFoundException extends ApplicationException {
    public InviteNotFoundException() {
        super("InviteNotFoundException", HttpStatus.NOT_FOUND);
    }
}