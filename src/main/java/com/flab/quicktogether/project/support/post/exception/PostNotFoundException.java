package com.flab.quicktogether.project.support.post.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class PostNotFoundException extends ApplicationException {
    public PostNotFoundException() {
        super("PostNotFoundException", HttpStatus.NOT_FOUND);
    }
}
