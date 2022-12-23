package com.flab.quicktogether.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ParticipantNotFoundException extends ApplicationException {
    public ParticipantNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ParticipantNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ParticipantNotFoundException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public ParticipantNotFoundException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }
}
