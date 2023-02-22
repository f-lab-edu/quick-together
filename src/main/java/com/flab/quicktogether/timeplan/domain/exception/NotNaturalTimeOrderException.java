package com.flab.quicktogether.timeplan.domain.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class NotNaturalTimeOrderException extends ApplicationException {
    public NotNaturalTimeOrderException() {
        super("NotNaturalTimeOrderException", HttpStatus.BAD_REQUEST);
    }
}
