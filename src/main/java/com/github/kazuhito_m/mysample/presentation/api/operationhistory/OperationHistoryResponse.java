package com.github.kazuhito_m.mysample.presentation.api.operationhistory;

import com.github.kazuhito_m.mysample.domain.model.operationhistory.OperationHistory;

import java.time.LocalDateTime;

public class OperationHistoryResponse {
    public final LocalDateTime createdAt;
    public final String clientIpAddress;
    public final String requestPath;
    public final String parameters;

    public OperationHistoryResponse(OperationHistory operationHistory) {
        createdAt = operationHistory.createdAt();
        clientIpAddress = operationHistory.clientIpAddress();
        requestPath = operationHistory.requestPath();
        parameters = operationHistory.parameters();
    }
}
