package com.flab.quicktogether.alarm.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class AlarmTokenNotFoundException extends ApplicationException {
    public AlarmTokenNotFoundException() {
        super("AlarmTokenNotFoundException", HttpStatus.NOT_FOUND);
    }
}
