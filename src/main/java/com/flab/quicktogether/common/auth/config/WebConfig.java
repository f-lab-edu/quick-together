package com.flab.quicktogether.common.auth.config;

import com.flab.quicktogether.common.auth.config.jwt.JwtProvider;
import com.flab.quicktogether.common.auth.config.session.SessionLoginCheckInterceptor;
import com.flab.quicktogether.common.auth.config.session.SessionLoginMemberIdArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(4);
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
                .addPathPatterns("/x/**")
                .excludePathPatterns("/","/ui/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .exposedHeaders("X-AUTH-TOKEN","Cookie","Set-Cookie");

    }

}
