package com.Tasker.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.StreamWriteConstraints;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Configuration class for customizing the Jackson ObjectMapper.
 *
 * @author Yons Said
 */
@Configuration
public class JacksonConfig {

    /**
     * Configures the Jackson ObjectMapper bean.
     *
     * @param builder the Jackson2ObjectMapperBuilder used to build the ObjectMapper.
     * @return a configured ObjectMapper bean.
     */
    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder
                .modules(new JavaTimeModule()) // Register the JavaTimeModule to handle Java 8 Date and Time API.
                .build();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Enable pretty-printing of JSON output.
        objectMapper.getFactory().setStreamWriteConstraints(
                StreamWriteConstraints.builder().maxNestingDepth(2000).build() // Set stream write constraints with a maximum nesting depth of 2000.
        );
        return objectMapper;
    }
}
