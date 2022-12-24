package com.flab.quicktogether.project.exception;

import com.flab.quicktogether.globalsetting.exception.ApplicationException;
import com.flab.quicktogether.globalsetting.exception.ErrorCode;

public class DuplicateProjectParticipationException extends ApplicationException {

    public DuplicateProjectParticipationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DuplicateProjectParticipationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public DuplicateProjectParticipationException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public DuplicateProjectParticipationException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }
}