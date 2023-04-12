package com.flab.quicktogether.project.support.chat.presentation;

import com.flab.quicktogether.project.support.chat.application.ProjectChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate template;
    private final ProjectChatService projectChatService;



    @MessageMapping("/chat")
    public void createPost(@Headers Map<String, Object> headers,
                           @RequestBody @Valid ChatMessage chatMessage) {

        template.convertAndSend("/topic/chat/" + chatMessage.getProjectId(), chatMessage);
        projectChatService.createChat(chatMessage.getProjectId(), chatMessage.getMemberId(), chatMessage.getContent());
        log.info("chatMessage = {}", chatMessage);
    }
}