package com.flab.quicktogether.participant.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import com.flab.quicktogether.globalsetting.domain.exception.ErrorCode;

public class DuplicateParticipantPositionException extends ApplicationException {
    public DuplicateParticipantPositionException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DuplicateParticipantPositionException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public DuplicateParticipantPositionException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public DuplicateParticipantPositionException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }
}
