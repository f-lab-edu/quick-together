package com.flab.quicktogether.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@OpenAPIDefinition
@Configuration
public class SpringDocConfig {

    @PostConstruct
    public void init(){
        Schema<LocalTime> schema = new Schema<>();
        schema.example(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))).type("string");
        SpringDocUtils.getConfig().replaceWithSchema(LocalTime.class, schema);
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
