package com.flab.quicktogether.project.support.search.presentation.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class PostInfoDto {
    String memberName;
    String content;
    LocalDateTime createDateTime;

    public PostInfoDto(String memberName, String content, LocalDateTime createDateTime) {
        this.memberName = memberName;
        this.content = content;
        this.createDateTime = createDateTime;
    }
}
