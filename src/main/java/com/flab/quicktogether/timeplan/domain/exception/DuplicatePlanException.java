package com.flab.quicktogether.timeplan.domain.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class DuplicatePlanException extends ApplicationException {
    public DuplicatePlanException() {
        super("DuplicatePlanException", HttpStatus.BAD_REQUEST);
    }
}
