package com.github.kazuhito_m.mysample.application.service.user;

import com.github.kazuhito_m.mysample.application.service.aspect.JoinPointDumper;
import com.github.kazuhito_m.mysample.application.service.logger.CustomLogger;
import com.github.kazuhito_m.mysample.presentation.requeststock.RequestStocks;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

@Component
public class UserServiceCustomLogger implements CustomLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceCustomLogger.class);

    private final RequestStocks requestStocks;

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
        writeLog();
    }

    private synchronized void writeLog() {
        Optional<HttpServletRequest> maybeRequest = requestStocks.get();
        maybeRequest.ifPresent(r -> {
            try (FileWriter fw = new FileWriter("/home/kazuhito/work/stacktrace_test/filter/test.log", true)) {
                fw.write("ThreadId: " + Thread.currentThread().getId());
                fw.write(" ,今、ストックされてるリクエスト数: " + requestStocks.size());
                fw.write(",url:" + r.getRequestURI());
                fw.write(",params:" + r.getQueryString());
                fw.write("\n");
            } catch (IOException e) {
            }
        });
    }

    public UserServiceCustomLogger(RequestStocks requestStocks) {
        this.requestStocks = requestStocks;
    }
}
