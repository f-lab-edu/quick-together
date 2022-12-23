package com.flab.quicktogether.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProjectNotFoundException extends ApplicationException{

    public ProjectNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ProjectNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ProjectNotFoundException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public ProjectNotFoundException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }
}
