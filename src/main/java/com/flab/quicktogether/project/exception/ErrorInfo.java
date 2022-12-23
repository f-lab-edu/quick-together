package com.flab.quicktogether.project.exception;

import lombok.Getter;

@Getter
public class ErrorInfo{
    private String filed;
    private Object rejectedValue;
    private String message;

    public ErrorInfo(String filed, Object rejectedValue, String message) {
        this.filed = filed;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }
}
