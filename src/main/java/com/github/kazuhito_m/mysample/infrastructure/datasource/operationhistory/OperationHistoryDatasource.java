package com.github.kazuhito_m.mysample.infrastructure.datasource.operationhistory;

import com.github.kazuhito_m.mysample.domain.model.operationhistory.OperationHistories;
import com.github.kazuhito_m.mysample.domain.model.operationhistory.OperationHistory;
import com.github.kazuhito_m.mysample.domain.model.operationhistory.OperationHistoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
public class OperationHistoryDatasource implements OperationHistoryRepository {
    final OperationHistoryDao dao;

    @Override
    public OperationHistories all() {
        List<OperationHistory> histories = dao.list()
                .stream()
                .map(OperationHistoryTable::toOperationHistory)
                .collect(toList());
        return new OperationHistories(histories);
    }


    @Override
    public void register(OperationHistory operationHistory) {
        long id = dao.publishIdentifier();
        dao.register(OperationHistoryTable.of(id, operationHistory));
    }

    public OperationHistoryDatasource(OperationHistoryDao dao) {
        this.dao = dao;
    }
}
