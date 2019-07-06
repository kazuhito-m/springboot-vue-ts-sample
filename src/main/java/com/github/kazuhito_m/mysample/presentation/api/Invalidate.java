package com.github.kazuhito_m.mysample.presentation.api;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.stream.Stream;

/**
 * Validationでerrorとなった場合のResponseBody。
 */
public class Invalidate {
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


    public Invalidate(HttpMessageNotReadableException e, HttpStatus httpStatus) {
        status = httpStatus.value();
        error = httpStatus.getReasonPhrase();
        errorCause = errorCauseOf(e);
    }

    private String errorCauseOf(HttpMessageNotReadableException e) {
        if (!(e.getCause() instanceof InvalidFormatException)) return "値の取得に失敗しました。書式が正しくありません。";

        InvalidFormatException ife = (InvalidFormatException) e.getCause();
        JsonMappingException.Reference firstRef = ife.getPath().get(0);
        String message = "値の取得に失敗しました。書式が正しくありません。";
        String fieldName = firstRef.getFieldName();
        String value = ife.getValue().toString();
        return String.format("%s [%s:'%s']", message, fieldName, value);
    }
}
