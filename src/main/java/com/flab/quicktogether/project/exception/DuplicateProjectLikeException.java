package com.flab.quicktogether.project.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class DuplicateProjectLikeException extends ApplicationException {
    public DuplicateProjectLikeException() {
        this.ERROR_CODE="DuplicateProjectLikeException";
        this.HTTP_STATUS=HttpStatus.BAD_REQUEST;
    }
}
