package com.github.kazuhito_m.mysample.presentation.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.stream.Stream;

/**
 * Validationでerrorとなった場合のResponseBody。
 */
public class Invalidate {
    private static final Logger LOGGER = LoggerFactory.getLogger(Invalidate.class);

    final LocalDateTime timestamp = LocalDateTime.now();
    final int status;
    final String error;
    final String errorCause;

    public Invalidate(MethodArgumentNotValidException e, HttpStatus httpStatus) {
        status = httpStatus.value();
        error = httpStatus.getReasonPhrase();
        errorCause = errorCauseOf(e);
    }

    private static String errorCauseOf(MethodArgumentNotValidException e) {
        ObjectError firstInvalid = e.getBindingResult().getAllErrors().get(0);

        LOGGER.info("getDefaultMessage:" + firstInvalid.getDefaultMessage());
        LOGGER.info("getObjectName:" + firstInvalid.getObjectName());
        LOGGER.info("getCode:" + firstInvalid.getCode());
        LOGGER.info("getCodes:");
        for (String code : firstInvalid.getCodes()) {
            LOGGER.info("    code:" + code);
        }
        LOGGER.info("getArguments:");
        for (Object o : firstInvalid.getArguments()) {
            LOGGER.info("    argument:" + o);
        }
        LOGGER.info("shouldRenderDefaultMessage:" + firstInvalid.shouldRenderDefaultMessage());
        LOGGER.info("toString自体:" + firstInvalid.toString());


        String message = firstInvalid.getDefaultMessage();
        String fieldName = fieldNameOf(firstInvalid);
        String value = originalValueOf(firstInvalid);
        return String.format("%s [%s:'%s']", message, fieldName, value);
    }

    private static String fieldNameOf(ObjectError invalid) {
        String fieldPrefix = invalid.getObjectName() + ".";
        return Stream.of(invalid.getCodes())
                .filter(i -> i.contains(invalid.getObjectName() + "."))
                .map(i -> i.replaceAll(".*" + fieldPrefix, ""))
                .findFirst()
                .orElse("値名不明");
    }

    private static String originalValueOf(ObjectError invalid) {
        return invalid.toString()
                .replaceAll(".*rejected value \\[", "")
                .replaceAll("\\];.*", "");
    }
}
