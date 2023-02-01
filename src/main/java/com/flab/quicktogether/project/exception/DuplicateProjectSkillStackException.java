package com.flab.quicktogether.project.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class DuplicateProjectSkillStackException extends ApplicationException {
    public DuplicateProjectSkillStackException() {
        super("DuplicateProjectSkillStackException", HttpStatus.BAD_REQUEST);
    }
}