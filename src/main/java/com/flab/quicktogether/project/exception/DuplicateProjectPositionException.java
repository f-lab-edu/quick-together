package com.flab.quicktogether.project.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class DuplicateProjectPositionException extends ApplicationException {
    public DuplicateProjectPositionException() {
        this.ERROR_CODE="DuplicateProjectPositionException";
        this.HTTP_STATUS= HttpStatus.BAD_REQUEST;
    }

}
