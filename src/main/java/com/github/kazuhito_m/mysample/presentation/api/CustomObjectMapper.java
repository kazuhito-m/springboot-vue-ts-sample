package com.github.kazuhito_m.mysample.presentation.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
        enable(SerializationFeature.INDENT_OUTPUT);
//        enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY); // GenericJackson2JsonRedisSerializer のコンストラクタ未指定時のデフォルト
        // JSR310 Date and Time API 変換対応 (LocalDate系)
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        registerModule(javaTimeModule);
    }
}
