package com.flab.quicktogether.project.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class JoinProjectException extends ApplicationException {
    public JoinProjectException() {
        super("JoinProjectException", HttpStatus.BAD_REQUEST);
    }
}
