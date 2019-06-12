package com.github.kazuhito_m.mysample.infrastructure.datasource.operationhistory;

import com.github.kazuhito_m.mysample.domain.model.operationhistory.OperationHistory;
import org.seasar.doma.*;

import java.time.LocalDateTime;

@Entity(immutable = true)
@Table(name = "operations.operation_histories")
public class OperationHistoryTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long operationHistoryId;
    private final String operationDescription;
    private final String clientIpAddress;
    private final String requestPath;
    private final String parameters;
    private final LocalDateTime createdAt;

    public OperationHistoryTable(Long operationHistoryId, String operationDescription, String clientIpAddress, String requestPath, String parameters, LocalDateTime createdAt) {
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

    public static OperationHistoryTable of(OperationHistory history) {
        return new OperationHistoryTable(
                null,
                history.operationDescription(),
                history.clientIpAddress(),
                history.requestPath(),
                history.parameters(),
                LocalDateTime.now()
        );
    }
}
