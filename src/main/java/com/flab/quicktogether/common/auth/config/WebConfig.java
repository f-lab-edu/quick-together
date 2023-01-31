package com.flab.quicktogether.common.auth.config;

import com.flab.quicktogether.common.auth.config.jwt.JwtLoginCheckInterceptor;
import com.flab.quicktogether.common.auth.config.jwt.JwtLoginMemberIdArgumentResolver;
import com.flab.quicktogether.common.auth.config.jwt.JwtProvider;
import com.flab.quicktogether.common.auth.config.session.SessionLoginCheckInterceptor;
import com.flab.quicktogether.common.auth.config.session.SessionLoginMemberIdArgumentResolver;
import com.flab.quicktogether.member.application.login.LoginService;
import com.flab.quicktogether.member.application.login.SessionLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider();
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SessionLoginMemberIdArgumentResolver());
        //resolvers.add(new JwtLoginMemberIdArgumentResolver(jwtProvider()));
    }



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(new SessionLoginCheckInterceptor())
                //.addInterceptor(new JwtLoginCheckInterceptor(jwtProvider()))
                .order(1)
                .addPathPatterns("/x/**");
    }
}
