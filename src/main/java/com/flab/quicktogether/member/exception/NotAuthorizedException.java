package com.flab.quicktogether.member.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class NotAuthorizedException extends ApplicationException {
    public NotAuthorizedException() {
        super("NotAuthorizedException", HttpStatus.UNAUTHORIZED);
    }
}
