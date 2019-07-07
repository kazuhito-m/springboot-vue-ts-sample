package com.github.kazuhito_m.mysample.application.service.operationhistory;

import com.github.kazuhito_m.mysample.domain.model.operationhistory.OperationHistories;
import com.github.kazuhito_m.mysample.domain.model.operationhistory.OperationHistory;
import com.github.kazuhito_m.mysample.domain.model.operationhistory.OperationHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class OperationHistoryService {
    final OperationHistoryRepository repository;

    public OperationHistories allOperationHistories() {
        return repository.all();
    }

    public void register(OperationHistory operationHistory) {
        repository.register(operationHistory);
    }

    OperationHistoryService(OperationHistoryRepository userRepository) {
        this.repository = userRepository;
    }
}
