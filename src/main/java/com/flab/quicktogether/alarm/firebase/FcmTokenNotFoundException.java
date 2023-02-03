package com.flab.quicktogether.alarm.firebase;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class FcmTokenNotFoundException extends ApplicationException {
    public FcmTokenNotFoundException() {
        super("FcmTokenNotFoundException", HttpStatus.NOT_FOUND);
    }
}
