package com.flab.quicktogether.timeplan.domain.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class AvailablePlanOverlapException extends ApplicationException {
    public AvailablePlanOverlapException() {
        super("AvailablePlanOverlapException", HttpStatus.BAD_REQUEST);
    }
}
