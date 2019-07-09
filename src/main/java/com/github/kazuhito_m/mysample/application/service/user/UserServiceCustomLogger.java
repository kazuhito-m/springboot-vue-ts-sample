package com.github.kazuhito_m.mysample.application.service.user;

import com.github.kazuhito_m.mysample.application.service.logger.CustomLogger;
import com.github.kazuhito_m.mysample.application.service.operationhistory.OperationHistoryService;
import com.github.kazuhito_m.mysample.domain.model.operationhistory.OperationHistory;
import com.github.kazuhito_m.mysample.presentation.requeststock.RequestStocks;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class UserServiceCustomLogger implements CustomLogger {
    private final OperationHistoryService operationHistoryService;
    private final RequestStocks requestStocks;

    @Override
    public Class<UserService> targetType() {
        return UserService.class;
    }

    @Override
    public void afterMethod(JoinPoint joinPoint, Object returnValue, Throwable e) {
//        new JoinPointDumper().dumpLog(joinPoint);
        String methodName = joinPoint.getSignature().getName();
        if (methodName.equals("inServicePrivateMethod")) afterInServicePrivateMethod(joinPoint, returnValue);
    }

    private void afterInServicePrivateMethod(JoinPoint joinPoint, Object returnValue) {
        String returnText = (String) returnValue;
        String logText = "APIにより「全ユーザの一覧」が要求されました。"
                + "method: inServicePrivateMethod が呼び出されました。"
                + " 引数:hikisuu=" + joinPoint.getArgs()[0]
                + ",戻り値:" + returnText;
        Optional<HttpServletRequest> maybeRequest = requestStocks.get();
        maybeRequest.ifPresent(request ->
                operationHistoryService.register(createOperationHistory(logText, request)));
    }

    private OperationHistory createOperationHistory(String description, HttpServletRequest request) {
        String params = request.getQueryString();
        if (params == null) params = "nothing";
        return new OperationHistory(
                description,
                request.getRemoteAddr(),
                request.getRequestURI(),
                params,
                LocalDateTime.now()
        );
    }

    public UserServiceCustomLogger(OperationHistoryService operationHistoryService, RequestStocks requestStocks) {
        this.operationHistoryService = operationHistoryService;
        this.requestStocks = requestStocks;
    }
}
