package com.flab.quicktogether.member.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class DuplicateMemberNameException extends ApplicationException {
    public DuplicateMemberNameException() {
        super("DuplicateMemberNameException", HttpStatus.BAD_REQUEST);
    }
}
