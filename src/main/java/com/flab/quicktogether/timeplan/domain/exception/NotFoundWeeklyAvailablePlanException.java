package com.flab.quicktogether.timeplan.domain.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class NotFoundWeeklyAvailablePlanException extends ApplicationException {
    public NotFoundWeeklyAvailablePlanException() {
        super("NotFoundWeeklyAvailablePlan", HttpStatus.NOT_FOUND);
    }
}
