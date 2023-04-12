package com.flab.quicktogether.project.support.chat.presentation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatMessage {

    private Long memberId;
    private String memberName;
    @NotEmpty
    @Size(min = 1, max = 300)
    private String content;
    private Long projectId;


}