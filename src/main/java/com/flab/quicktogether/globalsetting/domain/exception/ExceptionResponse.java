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
    private String message;
    private String path;

    private List<ErrorInfo> errors = new ArrayList<>();

    public ExceptionResponse(String message, String path) {
        this.message = message;
        this.path = path;
    }

    public ExceptionResponse(String message, String path,List<ObjectError> allErrors) {
        this.message = message;
        this.path = path;
        allErrors.stream()
                .map(allError -> (FieldError) allError)
                .forEach(fieldError -> errors.add(new ErrorInfo(fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage())));
    }
}
