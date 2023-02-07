package com.flab.quicktogether.alarm.firebase;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class AlarmTokenNotFoundException extends ApplicationException {
    public AlarmTokenNotFoundException() {
        super("AlarmTokenNotFoundException", HttpStatus.NOT_FOUND);
    }
}
