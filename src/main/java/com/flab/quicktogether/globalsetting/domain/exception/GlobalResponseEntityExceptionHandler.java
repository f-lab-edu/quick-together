package com.flab.quicktogether.globalsetting.domain.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;


@RestController
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    private final MessageSource message;

/*    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    @ExceptionHandler(ApplicationException.class)
    public final ResponseEntity<Object> handleApplicationException(ApplicationException ex, WebRequest request) {
        log.warn(ex.fillInStackTrace().getMessage());

        String message = this.message.getMessage(ex.getERROR_CODE(), null, null);
        String path = request.getDescription(false);
        ExceptionResponse exceptionResponse = new ExceptionResponse(message, path);
        return new ResponseEntity(exceptionResponse, ex.getHTTP_STATUS());
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        log.warn(ex.getClass().getSimpleName(), ex.getMessage());

        String message = this.message.getMessage("MethodArgumentNotValidException", null, null);
        String path = request.getDescription(false);
        ExceptionResponse exceptionResponse = new ExceptionResponse(message, path, ex.getAllErrors());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        log.warn(ex.getClass().getSimpleName(), ex.getMessage());

        String message=ex.getMessage();
        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException ifx = (InvalidFormatException) ex.getCause();
            if (ifx.getTargetType()!=null && ifx.getTargetType().isEnum()) {
                message = String.format("Invalid enum value: '%s' for the field: '%s'. The value must be one of: %s.",
                        ifx.getValue(), ifx.getPath().get(ifx.getPath().size()-1).getFieldName(), Arrays.toString(ifx.getTargetType().getEnumConstants()));
            }
        }

        String path = request.getDescription(false);
        ExceptionResponse exceptionResponse = new ExceptionResponse(message, path);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        HttpHeaders headers,
                                                        HttpStatusCode status,
                                                        WebRequest request) {
        log.warn(ex.fillInStackTrace().getMessage());
        String message = String.format("'%s' to required type '%s'",ex.getValue(),ex.getRequiredType());

        if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException castEx  = (MethodArgumentTypeMismatchException) ex;
            message = String.format("'%s' to required type '%s'",ex.getValue(),castEx.getName());
        }

        String path = request.getDescription(false);
        ExceptionResponse exceptionResponse = new ExceptionResponse(message, path);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }



}
