package com.flab.quicktogether.project.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import com.flab.quicktogether.globalsetting.domain.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProjectNoLikeException extends ApplicationException {

    public ProjectNoLikeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ProjectNoLikeException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ProjectNoLikeException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public ProjectNoLikeException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }
}
