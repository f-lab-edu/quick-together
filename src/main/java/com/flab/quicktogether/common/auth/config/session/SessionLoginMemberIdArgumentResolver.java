package com.flab.quicktogether.common.auth.config.session;

import com.flab.quicktogether.common.auth.Login;
import com.flab.quicktogether.member.exception.NotAuthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class SessionLoginMemberIdArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasParameterAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean assignableFrom = Long.class.isAssignableFrom(parameter.getParameterType());
        return hasParameterAnnotation && assignableFrom;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        HttpSession session = request.getSession(false);

        if (session == null) {
            throw new NotAuthorizedException();
        }

        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
