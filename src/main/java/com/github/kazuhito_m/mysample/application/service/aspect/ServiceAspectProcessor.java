package com.github.kazuhito_m.mysample.application.service.aspect;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceAspectProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAspectProcessor.class);

    public void after(JoinPoint joinPoint, Object returnValue, Throwable e) {
        LOGGER.info("メソッド後イベント: " + joinPoint.getTarget().getClass()
                + "#" + joinPoint.getSignature().getName());
        LOGGER.info("  戻り値:" + returnValue);
        LOGGER.info("  例外:" + e);
    }
}
