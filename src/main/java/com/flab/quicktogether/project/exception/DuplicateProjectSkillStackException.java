package com.flab.quicktogether.project.exception;

public class DuplicateProjectSkillStackException extends RuntimeException {
    public DuplicateProjectSkillStackException() {
    }

    public DuplicateProjectSkillStackException(String message) {
        super(message);
    }

    public DuplicateProjectSkillStackException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateProjectSkillStackException(Throwable cause) {
        super(cause);
    }

    public DuplicateProjectSkillStackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
