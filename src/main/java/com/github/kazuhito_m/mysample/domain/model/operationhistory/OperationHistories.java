package com.github.kazuhito_m.mysample.domain.model.operationhistory;

import java.util.List;

public class OperationHistories {
    List<OperationHistory> values;

    public List<OperationHistory> list() {
        return values;
    }

    public OperationHistories(List<OperationHistory> values) {
        this.values = values;
    }
}
