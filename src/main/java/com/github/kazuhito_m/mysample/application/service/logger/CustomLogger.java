package com.github.kazuhito_m.mysample.application.service.logger;

import org.aspectj.lang.JoinPoint;

public interface CustomLogger {
    Class<?> targetType();

    void afterMethod(JoinPoint joinPoint, Object returnValue, Throwable e);
}
