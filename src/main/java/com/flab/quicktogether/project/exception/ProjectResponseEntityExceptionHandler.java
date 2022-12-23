package com.flab.quicktogether.project.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
@Slf4j
public class ProjectResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

/*    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    @ExceptionHandler(ApplicationException.class)
    public final ResponseEntity<Object> handleApplicationException(ApplicationException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getErrorCode(),request.getDescription(false));
        return new ResponseEntity(exceptionResponse, ex.getErrorCode().getHttpStatus());
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        log.warn(ex.getClass().getSimpleName(), ex.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(status.value(),"Validation Failed", ex.getBindingResult().toString(),request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        log.warn(ex.getClass().getSimpleName(), ex.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(status.value(),"Failed to read request", ex.getMessage(),request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }



}
