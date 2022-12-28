package com.flab.quicktogether.project.exception;

import com.flab.quicktogether.globalsetting.exception.ApplicationException;
import com.flab.quicktogether.globalsetting.exception.ErrorCode;

public class DuplicateProjectLikeException extends ApplicationException {
    public DuplicateProjectLikeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DuplicateProjectLikeException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public DuplicateProjectLikeException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public DuplicateProjectLikeException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }
}
