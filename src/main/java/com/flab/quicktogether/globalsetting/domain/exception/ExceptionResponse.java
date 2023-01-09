package com.flab.quicktogether.globalsetting.domain.exception;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ExceptionResponse {
    private LocalDateTime timestamp = LocalDateTime.now();

    private int status;
    private String error = "";
    private String code = "";
    private String message = "";
    private String path = "";

    private List<ErrorInfo> errors = new ArrayList<>();

    public ExceptionResponse(ErrorCode errorCode, String path) {
        this.status = errorCode.getHttpStatus().value();
        this.error = errorCode.getHttpStatus().name();
        this.message = errorCode.getMessage();
        this.code = errorCode.name();
        this.path = path;
    }

    public ExceptionResponse(ErrorCode errorCode, String message, String path) {
        this.status = errorCode.getHttpStatus().value();
        this.error = errorCode.getHttpStatus().name();
        this.message = message;
        this.code = errorCode.name();
        this.path = path;
    }

    public ExceptionResponse(ErrorCode errorCode, String path,List<ObjectError> allErrors) {
        this.status = errorCode.getHttpStatus().value();
        this.error = errorCode.getHttpStatus().name();
        this.message = errorCode.getMessage();
        this.code = errorCode.name();
        this.path = path;
        allErrors.stream()
                .map(allError -> (FieldError) allError)
                .forEach(fieldError -> errors.add(new ErrorInfo(fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage())));
    }
}
