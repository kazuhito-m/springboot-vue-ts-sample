package com.github.kazuhito_m.mysample.application.service.aspect;

import com.github.kazuhito_m.mysample.application.service.logger.CustomLogger;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Component
public class ServiceAspectProcessor implements ApplicationContextAware {
    // FIXME SpringDIとAspectJでのnewとを融合するために、行儀悪いことをしている。なんとかできないか。
    private static ApplicationContext applicationContext;

    private Map<Class<?>, CustomLogger> loggersCache = null;

    public void after(JoinPoint joinPoint, Object returnValue, Throwable e) {
        Map<Class<?>, CustomLogger> loggers = lookupLoggers();
        CustomLogger logger = loggers.get(joinPoint.getThis().getClass());
        if (logger == null) return;
        logger.afterMethod(joinPoint, returnValue, e);
    }

    private Map<Class<?>, CustomLogger> lookupLoggers() {
        if (loggersCache == null) loggersCache = lookupCustomLoggers();
        return loggersCache;
    }

    private Map<Class<?>, CustomLogger> lookupCustomLoggers() {
        return applicationContext.getBeansOfType(CustomLogger.class)
                .values()
                .stream()
                .collect(toMap(
                        CustomLogger::targetType,
                        i -> i
                ));
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        applicationContext = ac;
    }
}
