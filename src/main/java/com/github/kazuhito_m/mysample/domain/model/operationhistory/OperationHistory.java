package com.github.kazuhito_m.mysample.domain.model.operationhistory;

import java.time.LocalDateTime;

public class OperationHistory {
    String operationDescription;
    String clientIpAddress;
    String requestPath;
    String parameters;
    LocalDateTime createdAt;

    public OperationHistory(String operationDescription, String clientIpAddress, String requestPath, String parameters, LocalDateTime createdAt) {
        this.operationDescription = operationDescription;
        this.clientIpAddress = clientIpAddress;
        this.requestPath = requestPath;
        this.parameters = parameters;
        this.createdAt = createdAt;
    }

    public String operationDescription() {
        return operationDescription;
    }

    public String clientIpAddress() {
        return clientIpAddress;
    }

    public String requestPath() {
        return requestPath;
    }

    public String parameters() {
        return parameters;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }
}
