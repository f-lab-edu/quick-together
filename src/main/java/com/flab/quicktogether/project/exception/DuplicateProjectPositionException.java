package com.flab.quicktogether.project.exception;

import com.flab.quicktogether.globalsetting.exception.ApplicationException;
import com.flab.quicktogether.globalsetting.exception.ErrorCode;

public class DuplicateProjectPositionException extends ApplicationException {
    public DuplicateProjectPositionException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DuplicateProjectPositionException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public DuplicateProjectPositionException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public DuplicateProjectPositionException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }
}
