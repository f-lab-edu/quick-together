package com.flab.quicktogether.meeting.presentation.dto;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Entity 조회시 조회 이후에 할수있는 각종 행동 버튼을 프론트에서 편하게 구현할수있도록
 * path가 무엇인지 HATEOAS를 통해 구현시도중
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Link {
    private final String rel;
    private final String href;

    public static Link of(String rel, String href, Object ... pathVariables) {
        String uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(href)
                .buildAndExpand(pathVariables)
                .toUriString();
        return new Link(rel, uri);
    }
}
