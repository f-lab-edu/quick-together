package com.flab.quicktogether.timeplan.domain.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class NotFoundPlanException extends ApplicationException {
    public NotFoundPlanException() {
        super("NotFoundPlanException", HttpStatus.NOT_FOUND);
    }
}
