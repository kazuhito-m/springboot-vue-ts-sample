package com.github.kazuhito_m.mysample.presentation.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
        enable(SerializationFeature.INDENT_OUTPUT);
        // enableDefaultTyping(DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY); // GenericJackson2JsonRedisSerializer のコンストラクタ未指定時のデフォルト
        // JSR310 Date and Time API 変換対応 (LocalDate系)
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(formatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(formatter));
        registerModule(javaTimeModule);
        // JSONの設定
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // Setter/Getterは無視、フィールドのみprivateでも入出力する。
        setVisibility(PropertyAccessor.ALL, Visibility.NONE);
        setVisibility(PropertyAccessor.FIELD, Visibility.NON_PRIVATE);
    }
}
