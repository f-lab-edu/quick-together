package com.flab.quicktogether.globalsetting.domain.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException{
    private final ErrorCode errorCode;

    public ApplicationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ApplicationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApplicationException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ApplicationException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }
}
