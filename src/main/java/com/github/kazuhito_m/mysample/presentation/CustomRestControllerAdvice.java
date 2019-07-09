package com.github.kazuhito_m.mysample.presentation;

import com.github.kazuhito_m.mysample.domain.basic.DataNotExistsException;
import org.seasar.doma.jdbc.UniqueConstraintException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class CustomRestControllerAdvice {
    @InitBinder
    private void initDirectFieldAccess(DataBinder dataBinder) {
        dataBinder.initDirectFieldAccess();
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private Invalidate handleException(BindException e) {
        return new Invalidate(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private Invalidate handleException(MethodArgumentNotValidException e) {
        return new Invalidate(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private Invalidate handleException(HttpMessageNotReadableException e) {
        return new Invalidate(e, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private Invalidate handleException(ConstraintViolationException e) {
        return new Invalidate(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private Invalidate handleException(MissingServletRequestParameterException e) {
        return new Invalidate(e, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UniqueConstraintException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    private Invalidate handleException(UniqueConstraintException e) {
        return new Invalidate(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private Invalidate handleException(DataNotExistsException e) {
        return new Invalidate(e, HttpStatus.NOT_FOUND);
    }
}
