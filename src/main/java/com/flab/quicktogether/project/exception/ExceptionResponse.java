package com.flab.quicktogether.project.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class ExceptionResponse {
    private LocalDateTime timestamp = LocalDateTime.now();

    private int status;
    private String error;
    private String code;
    private String message;
    private String path;

    public ExceptionResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ExceptionResponse(ErrorCode errorCode, String path) {
        this.status = errorCode.getHttpStatus().value();
        this.error = errorCode.getHttpStatus().name();
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
        this.path = path;
    }
}
