package com.flab.quicktogether.timeplan.domain.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class NotMatchableTimeException extends ApplicationException {
    public NotMatchableTimeException() {
        super("NotMatchableTimeException", HttpStatus.BAD_REQUEST);
    }
}
