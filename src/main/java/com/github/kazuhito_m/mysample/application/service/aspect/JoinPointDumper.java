package com.github.kazuhito_m.mysample.application.service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JoinPointDumper {
    private static final Logger LOGGER = LoggerFactory.getLogger(JoinPointDumper.class);

    public void dumpLog(JoinPoint joinPoint) {
        LOGGER.info("afterで出力されるもの。");
        LOGGER.info("getKind:" + joinPoint.getKind());
        LOGGER.info("getArgs:" + joinPoint.getArgs());
        LOGGER.info("getSourceLocation:" + joinPoint.getSourceLocation());
        LOGGER.info("getStaticPart:" + joinPoint.getStaticPart());
        LOGGER.info("getTarget:" + joinPoint.getTarget());
        LOGGER.info("getThis:" + joinPoint.getThis());
        LOGGER.info("toLongString:" + joinPoint.toLongString());
        LOGGER.info("toShortString:" + joinPoint.toShortString());
        LOGGER.info("toString:" + joinPoint.toString());
        LOGGER.info("getSignature:" + joinPoint.getSignature());
        dumpLogSignature(joinPoint.getSignature());
    }

    private void dumpLogSignature(Signature signature) {
        LOGGER.info("    getName:" + signature.getName());
        LOGGER.info("    getDeclaringTypeName:" + signature.getDeclaringTypeName());
        LOGGER.info("    getDeclaringType:" + signature.getDeclaringType());
        LOGGER.info("    toLongString:" + signature.toLongString());
        LOGGER.info("    toShortString:" + signature.toShortString());
        LOGGER.info("    getModifiers:" + signature.getModifiers());
    }
}
