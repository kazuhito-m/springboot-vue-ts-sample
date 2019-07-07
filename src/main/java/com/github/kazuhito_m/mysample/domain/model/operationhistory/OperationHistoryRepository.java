package com.github.kazuhito_m.mysample.domain.model.operationhistory;

public interface OperationHistoryRepository {
    OperationHistories all();

    void register(OperationHistory operationHistory);
}
