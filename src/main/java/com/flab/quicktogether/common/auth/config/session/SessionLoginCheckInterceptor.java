package com.flab.quicktogether.common.auth.config.session;

import com.flab.quicktogether.common.SessionConst;
import com.flab.quicktogether.member.exception.NotAuthorizedException;
import com.flab.quicktogether.common.auth.NotRequiredLoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class SessionLoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod && ((HandlerMethod) handler).hasMethodAnnotation(NotRequiredLoginCheck.class)) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            throw new NotAuthorizedException();
        }

        return true;
    }
}
