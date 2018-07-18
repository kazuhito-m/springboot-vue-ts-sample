package com.github.kazuhito_m.mysample.presentation.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As;

public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
        enable(SerializationFeature.INDENT_OUTPUT);
//        enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY); // GenericJackson2JsonRedisSerializer のコンストラクタ未指定時のデフォルト
        // JSR310 Date and Time API 変換対応 (LocalDate系)
        registerModule(new JavaTimeModule());
    }
}
