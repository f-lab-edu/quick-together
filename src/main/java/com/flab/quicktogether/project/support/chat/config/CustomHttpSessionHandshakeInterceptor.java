package com.flab.quicktogether.project.support.chat.config;

import com.flab.quicktogether.common.auth.config.session.SessionConst;
import com.flab.quicktogether.member.exception.NotAuthorizedException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;


public class CustomHttpSessionHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {


        HttpSession httpSession = getSession(request);
        if (httpSession == null || httpSession.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            throw new NotAuthorizedException();
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
    }

    private HttpSession getSession(ServerHttpRequest request) {
        return ((ServletServerHttpRequest)request).getServletRequest().getSession();
    }


}