package com.flab.quicktogether.project.exception;

public class DuplicateProjectParticipationException extends RuntimeException {

    public DuplicateProjectParticipationException() {
    }

    public DuplicateProjectParticipationException(String message) {
        super(message);
    }

    public DuplicateProjectParticipationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateProjectParticipationException(Throwable cause) {
        super(cause);
    }

    public DuplicateProjectParticipationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
