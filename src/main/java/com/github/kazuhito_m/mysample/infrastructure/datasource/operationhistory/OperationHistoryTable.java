package com.github.kazuhito_m.mysample.infrastructure.datasource.operationhistory;

import com.github.kazuhito_m.mysample.domain.model.operationhistory.OperationHistory;
import org.seasar.doma.*;

import java.time.LocalDateTime;

@Entity(immutable = true)
@Table(name = "operations.operation_histories")
public class OperationHistoryTable {
    @Id
    private final long operationHistoryId;
    private final String operationDescription;
    private final String clientIpAddress;
    private final String requestPath;
    private final String parameters;
    private final LocalDateTime createdAt;

    public OperationHistoryTable(long operationHistoryId, String operationDescription, String clientIpAddress, String requestPath, String parameters, LocalDateTime createdAt) {
        this.operationHistoryId = operationHistoryId;
        this.operationDescription = operationDescription;
        this.clientIpAddress = clientIpAddress;
        this.requestPath = requestPath;
        this.parameters = parameters;
        this.createdAt = createdAt;
    }


    public OperationHistory toOperationHistory() {
        return new OperationHistory(
                operationDescription,
                clientIpAddress,
                requestPath,
                parameters,
                createdAt
        );
    }

    public static OperationHistoryTable of(long operationHistoryId, OperationHistory history) {
        return new OperationHistoryTable(
                operationHistoryId,
                history.operationDescription(),
                history.clientIpAddress(),
                history.requestPath(),
                history.parameters(),
                LocalDateTime.now()
        );
    }
}
