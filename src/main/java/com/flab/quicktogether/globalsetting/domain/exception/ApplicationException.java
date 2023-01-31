package com.flab.quicktogether.globalsetting.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException{
    protected String errorCode;
    protected HttpStatus httpStatus;

    public ApplicationException(String errorCode, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
