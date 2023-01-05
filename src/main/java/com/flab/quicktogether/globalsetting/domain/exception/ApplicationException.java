package com.flab.quicktogether.globalsetting.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException{
    protected String ERROR_CODE;
    protected HttpStatus HTTP_STATUS;


}
