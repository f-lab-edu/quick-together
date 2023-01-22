package com.flab.quicktogether.project.support.enter.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class DuplicateEnterMemberException extends ApplicationException {
    public DuplicateEnterMemberException() {
        super("DuplicateEnterMemberException", HttpStatus.BAD_REQUEST);
    }
}
