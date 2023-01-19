package com.flab.quicktogether.common.auth.config.jwt;

import com.flab.quicktogether.common.auth.NotRequiredLoginCheck;
import com.flab.quicktogether.member.exception.NotAuthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class JwtLoginCheckInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod && ((HandlerMethod) handler).hasMethodAnnotation(NotRequiredLoginCheck.class)) {
            return true;
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null) {
            throw new NotAuthorizedException();
        }
        jwtProvider.validateToken(authorizationHeader);


        return true;
    }

}
