package com.github.kazuhito_m.mysample.presentation;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.seasar.doma.jdbc.UniqueConstraintException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
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


    public Invalidate(BindException e, HttpStatus httpStatus) {
        status = httpStatus.value();
        error = httpStatus.getReasonPhrase();
        errorCause = errorCauseOf(e.getFieldError());
    }

    private static String errorCauseOf(FieldError invalid) {
        String message = invalid.getDefaultMessage();
        // 型がFieldError以外の場合、自前で用意したValidationに引っかかってる…ということはFieldErrorなら「型変換失敗」なので、ぼやかす。
        if (invalid.getClass().getName().contains("FieldError")) message = "書式が正しくありません。";
        String fieldName = invalid.getField();
        return String.format("%s [%s:'%s']", message, fieldName, invalid.getRejectedValue());
    }


    public Invalidate(MethodArgumentNotValidException e, HttpStatus httpStatus) {
        status = httpStatus.value();
        error = httpStatus.getReasonPhrase();
        ObjectError firstInvalid = e.getBindingResult().getFieldError();
        errorCause = errorCauseOf(firstInvalid);
    }

    private static String errorCauseOf(ObjectError invalid) {
        String message = invalid.getDefaultMessage();
        String fieldName = fieldNameOf(invalid);
        String value = originalValueOf(invalid);
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
        if (!(e.getCause() instanceof InvalidFormatException)) return "書式が正しくありません。";

        InvalidFormatException ife = (InvalidFormatException) e.getCause();
        JsonMappingException.Reference firstRef = ife.getPath().get(0);
        String message = "書式が正しくありません。";
        String fieldName = firstRef.getFieldName();
        String value = ife.getValue().toString();
        return String.format("%s [%s:'%s']", message, fieldName, value);
    }

    public Invalidate(UniqueConstraintException e, HttpStatus httpStatus) {
        status = httpStatus.value();
        error = httpStatus.getReasonPhrase();
        errorCause = "指定されたデータはすでに存在しています。";
    }
}
