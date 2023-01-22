package com.flab.quicktogether.project.support.enter.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class EnterNotFoundException extends ApplicationException {
    public EnterNotFoundException() {
        super("EnterNotFoundException", HttpStatus.NOT_FOUND);
    }
}
