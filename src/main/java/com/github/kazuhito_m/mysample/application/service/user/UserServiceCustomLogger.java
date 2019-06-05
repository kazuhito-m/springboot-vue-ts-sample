package com.github.kazuhito_m.mysample.application.service.user;

import com.github.kazuhito_m.mysample.application.service.aspect.JoinPointDumper;
import com.github.kazuhito_m.mysample.application.service.logger.CustomLogger;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserServiceCustomLogger implements CustomLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceCustomLogger.class);

    @Override
    public Class<UserService> targetType() {
        return UserService.class;
    }

    @Override
    public void afterMethod(JoinPoint joinPoint, Object returnValue, Throwable e) {
        new JoinPointDumper().dumpLog(joinPoint);
        String methodName = joinPoint.getSignature().getName();
        if (methodName.equals("inServicePrivateMethod")) afterInServicePrivateMethod(joinPoint, returnValue);
    }

    private void afterInServicePrivateMethod(JoinPoint joinPoint, Object returnValue) {
        String returnText = (String) returnValue;
        String logText = "method: inServicePrivateMethod が呼び出されました。"
                + " 引数:hikisuu=" + joinPoint.getArgs()[0]
                + ",戻り値:" + returnText;
        LOGGER.info(logText);

        StackWalker stackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        stackWalker.walk(s -> s.collect(Collectors.toList()))
                .forEach(s -> LOGGER.info("呼び出し元:" + s));
    }
}
