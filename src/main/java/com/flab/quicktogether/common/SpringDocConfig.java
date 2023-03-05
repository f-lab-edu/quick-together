package com.flab.quicktogether.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.google.firebase.database.util.JsonMapper;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;

@Slf4j
@OpenAPIDefinition
@Configuration
public class SpringDocConfig {

    private final ObjectMapper objectMapper;

    @Autowired
    public SpringDocConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public OpenAPI baseOpenAPI(){
        return new OpenAPI().info(new Info()
                .title("quick-together")
                .version("0.0.1")
                .description("Quick-Together API 명세서입니다."));
    }

//    private JsonMapper getJsonMapperFromObjectMapper(){
//        Json.mapper().copy()
//    }
}
