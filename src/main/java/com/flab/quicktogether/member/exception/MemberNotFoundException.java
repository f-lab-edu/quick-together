package com.flab.quicktogether.member.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import com.flab.quicktogether.globalsetting.domain.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MemberNotFoundException extends ApplicationException {

    public MemberNotFoundException() {
        this.ERROR_CODE="MemberNotFoundException";
        this.HTTP_STATUS=HttpStatus.NOT_FOUND;
    }
}
