package com.flab.quicktogether.timeplan.domain.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class NoUniqueWeeklyAvailablePlanCreateException extends ApplicationException {
    public NoUniqueWeeklyAvailablePlanCreateException() {
        super("NoUniqueWeeklyAvailablePlanCreateException", HttpStatus.BAD_REQUEST);
    }
}
