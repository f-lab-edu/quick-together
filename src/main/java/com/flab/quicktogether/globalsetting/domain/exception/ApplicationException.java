package com.flab.quicktogether.globalsetting.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException{
    private final String errorCode;
    private final HttpStatus httpStatus;

    public ApplicationException(String errorCode, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
