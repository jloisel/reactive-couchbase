package com.github.jloisel.reactive.conversion.jackson;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.MapperFeature.SORT_PROPERTIES_ALPHABETICALLY;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_ENUMS_USING_TO_STRING;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
class JacksonConfig {

  @Bean
  public static ObjectMapper objectMapper() {
    final ObjectMapper mapper = new ObjectMapper();
    mapper.findAndRegisterModules();
    mapper.setSerializationInclusion(NON_NULL);
    mapper.enable(WRITE_ENUMS_USING_TO_STRING);
    mapper.enable(SORT_PROPERTIES_ALPHABETICALLY);
    return mapper;
  }
}
