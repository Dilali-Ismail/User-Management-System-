package org.userManagement.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.userManagement.controller")
public class WebConfig implements WebMvcConfigurer {
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
        ObjectMapper objectMapper = new ObjectMapper();
        // Ajoute le support pour les types Java 8 Time (comme LocalDateTime)
        objectMapper.registerModule(new JavaTimeModule());

        // Ajoute le convertisseur Jackson à la liste des convertisseurs de Spring
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
    }

}
