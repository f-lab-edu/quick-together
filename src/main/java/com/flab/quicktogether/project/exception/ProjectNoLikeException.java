package com.flab.quicktogether.project.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProjectNoLikeException extends ApplicationException {

    public ProjectNoLikeException() {
        this.ERROR_CODE = "ProjectNoLikeException";
        this.HTTP_STATUS = HttpStatus.BAD_REQUEST;
    }
}
