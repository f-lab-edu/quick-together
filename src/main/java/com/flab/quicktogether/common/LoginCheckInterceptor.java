package com.flab.quicktogether.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*if (handler instanceof HandlerMethod && ((HandlerMethod) handler).hasMethodAnnotation(NotRequiredLoginCheck.class)) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            throw new NotAuthorizedException();
        }*/

        return true;
    }
}
