package com.flab.quicktogether.project.exception;

public class DuplicateParticipantSkillStackException extends ApplicationException {
    public DuplicateParticipantSkillStackException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DuplicateParticipantSkillStackException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public DuplicateParticipantSkillStackException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public DuplicateParticipantSkillStackException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }
}
