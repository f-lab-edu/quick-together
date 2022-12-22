package com.flab.quicktogether.project.exception;

public class DuplicateParticipantPositionException extends RuntimeException {
    public DuplicateParticipantPositionException() {
    }

    public DuplicateParticipantPositionException(String message) {
        super(message);
    }

    public DuplicateParticipantPositionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateParticipantPositionException(Throwable cause) {
        super(cause);
    }

    public DuplicateParticipantPositionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
