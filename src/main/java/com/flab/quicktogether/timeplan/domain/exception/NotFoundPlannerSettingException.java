package com.flab.quicktogether.timeplan.domain.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class NotFoundPlannerSettingException extends ApplicationException {
    public NotFoundPlannerSettingException() {
        super("NotFoundPlannerSettingException", HttpStatus.NOT_FOUND);
    }

}
