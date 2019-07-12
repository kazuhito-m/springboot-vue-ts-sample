package com.github.kazuhito_m.mysample.infrastructure.datasource.operationhistory;

import com.github.kazuhito_m.mysample.infrastructure.multidbsetting.ConfigAutowireableForConnectToLogDatabase;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.jdbc.Result;

import java.util.List;

@Dao
@ConfigAutowireableForConnectToLogDatabase
public interface OperationHistoryDao {
    @Select
    Long publishIdentifier();

    @Select
    List<OperationHistoryTable> list();

    @Insert
    Result<OperationHistoryTable> register(OperationHistoryTable operationHistory);
}
