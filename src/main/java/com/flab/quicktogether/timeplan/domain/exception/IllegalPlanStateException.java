package com.flab.quicktogether.timeplan.domain.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class IllegalPlanStateException extends ApplicationException {
    public IllegalPlanStateException() {
        super("IllegalPlanStateException", HttpStatus.BAD_REQUEST);
    }

}
