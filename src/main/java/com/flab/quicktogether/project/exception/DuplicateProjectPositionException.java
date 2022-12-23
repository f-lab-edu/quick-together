package com.flab.quicktogether.project.exception;

public class DuplicateProjectPositionException extends RuntimeException {
    public DuplicateProjectPositionException() {
    }

    public DuplicateProjectPositionException(String message) {
        super(message);
    }

    public DuplicateProjectPositionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateProjectPositionException(Throwable cause) {
        super(cause);
    }

    public DuplicateProjectPositionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
