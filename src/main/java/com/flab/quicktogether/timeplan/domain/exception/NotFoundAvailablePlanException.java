package com.flab.quicktogether.timeplan.domain.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class NotFoundAvailablePlanException extends ApplicationException {
    public NotFoundAvailablePlanException() {
        super("NotFoundAvailablePlanException", HttpStatus.NOT_FOUND);
    }
}
