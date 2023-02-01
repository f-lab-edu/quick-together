package com.flab.quicktogether.project.support.post.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class ContentLengthException extends ApplicationException {
    public ContentLengthException() {
        super("ContentLengthException", HttpStatus.BAD_REQUEST);
    }
}
