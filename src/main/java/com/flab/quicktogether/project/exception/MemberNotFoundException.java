package com.flab.quicktogether.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MemberNotFoundException extends ApplicationException{

    public MemberNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
