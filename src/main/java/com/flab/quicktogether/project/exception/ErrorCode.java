package com.flab.quicktogether.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 멤버 정보를 찾을 수 없습니다."),
    PROJECT_NOT_FOUND(NOT_FOUND,"해당 프로젝트 정보를 찾을 수 없습니다."),
    PARTICIPANT_NOT_FOUND(NOT_FOUND,"해당 구성원 정보를 찾을 수 없습니다."),

    DUPLICATE_PARTICIPANT_POSITION(BAD_REQUEST, "이미 존재하는 포지션입니다."),
    DUPLICATE_PARTICIPANT_SKILLSTACK(BAD_REQUEST, "이미 존재하는 기술스택입니다."),
    DUPLICATE_PROJECT_POSITION(BAD_REQUEST, "이미 존재하는 포지션입니다."),
    DUPLICATE_PROJECT_SKILLSTACK(BAD_REQUEST, "이미 존재하는 기술스택입니다."),

    DUPLICATE_PROJECT_PARTICIPATION(BAD_REQUEST, "해당 멤버는 이미 프로젝트에 참여중 입니다.");




    private final HttpStatus httpStatus;
    private final String message;
}
