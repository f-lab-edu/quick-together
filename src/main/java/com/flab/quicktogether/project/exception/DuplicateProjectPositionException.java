package com.flab.quicktogether.project.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class DuplicateProjectPositionException extends ApplicationException {
    public DuplicateProjectPositionException() {
        super("DuplicateProjectPositionException", HttpStatus.BAD_REQUEST);
    }
}
