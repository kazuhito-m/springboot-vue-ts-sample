package com.github.kazuhito_m.mysample.presentation;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.github.kazuhito_m.mysample.domain.basic.DataNotExistsException;
import org.seasar.doma.jdbc.UniqueConstraintException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Validationでerrorとなった場合のResponseBody。
 */
public class Invalidate {
    final LocalDateTime timestamp = LocalDateTime.now();
    final int status;
    final String error;
    final String errorCause;

    private Invalidate(String errorCause, HttpStatus httpStatus) {
        status = httpStatus.value();
        error = httpStatus.getReasonPhrase();
        this.errorCause = errorCause;
    }

    public Invalidate(BindException e, HttpStatus httpStatus) {
        this(errorCauseOf(e.getFieldError()), httpStatus);
    }

    private static String errorCauseOf(FieldError invalid) {
        String message = invalid.getDefaultMessage();
        // 型がFieldError以外の場合、自前で用意したValidationに引っかかってる…ということはFieldErrorなら「型変換失敗」なので、ぼやかす。
        if (invalid.getClass().getName().contains("FieldError")) message = "書式が正しくありません。";
        String fieldName = invalid.getField();
        return String.format("%s [%s:'%s']", message, fieldName, invalid.getRejectedValue());
    }

    public Invalidate(MethodArgumentNotValidException e, HttpStatus httpStatus) {
        this(errorCauseOf(e.getBindingResult().getFieldError()), httpStatus);
    }

    public Invalidate(HttpMessageNotReadableException e, HttpStatus httpStatus) {
        this(errorCauseOf(e), httpStatus);
    }

    private static String errorCauseOf(HttpMessageNotReadableException e) {
        if (!(e.getCause() instanceof InvalidFormatException)) return "書式が正しくありません。";

        InvalidFormatException ife = (InvalidFormatException) e.getCause();
        JsonMappingException.Reference firstRef = ife.getPath().get(0);
        String message = "書式が正しくありません。";
        String fieldName = firstRef.getFieldName();
        String value = ife.getValue().toString();
        return String.format("%s [%s:'%s']", message, fieldName, value);
    }

    public Invalidate(ConstraintViolationException e, HttpStatus httpStatus) {
        this(errorCauseOf(e.getConstraintViolations()), httpStatus);
    }

    private static String errorCauseOf(Set<ConstraintViolation<?>> constraintViolations) {
        return constraintViolations.stream()
                .map(Invalidate::errorCauseOf)
                .findFirst()
                .orElse("原因不明。");
    }

    private static String errorCauseOf(ConstraintViolation violation) {
        String message = violation.getMessage();
        String fieldName = violation.getPropertyPath().toString()
                .replaceAll("\\.value", "")
                .replaceAll(".*\\.", "");
        return String.format("%s [%s:'%s']", message, fieldName, violation.getInvalidValue());
    }

    public Invalidate(MissingServletRequestParameterException e, HttpStatus httpStatus) {
        this(String.format("%s [%s]", "必須パラメータが指定されていません。", e.getParameterName()), httpStatus);
    }

    public Invalidate(UniqueConstraintException e, HttpStatus httpStatus) {
        this("指定されたデータはすでに存在しています。", httpStatus);
    }

    public Invalidate(DataNotExistsException e, HttpStatus httpStatus) {
        this("指定されたデータは存在しません。", httpStatus);
    }
}
