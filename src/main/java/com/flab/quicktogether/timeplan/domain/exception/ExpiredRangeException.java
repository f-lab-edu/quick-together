package com.flab.quicktogether.timeplan.domain.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class ExpiredRangeException extends ApplicationException {
    public ExpiredRangeException() {
        super("ExpiredRangeException", HttpStatus.BAD_REQUEST);
    }
}
